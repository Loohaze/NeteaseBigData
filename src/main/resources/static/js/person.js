window.onload=function () {
    Date.prototype.pattern=function(fmt) {
        let o = {
            "M+" : this.getMonth()+1, //月份
            "d+" : this.getDate(), //日
            "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时
            "H+" : this.getHours(), //小时
            "m+" : this.getMinutes(), //分
            "s+" : this.getSeconds(), //秒
            "q+" : Math.floor((this.getMonth()+3)/3), //季度
            "S" : this.getMilliseconds() //毫秒
        };
        let week = {
            "0" : "/u65e5",
            "1" : "/u4e00",
            "2" : "/u4e8c",
            "3" : "/u4e09",
            "4" : "/u56db",
            "5" : "/u4e94",
            "6" : "/u516d"
        };
        if(/(y+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
        }
        if(/(E+)/.test(fmt)){
            fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);
        }
        for(var k in o){
            if(new RegExp("("+ k +")").test(fmt)){
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
            }
        }
        return fmt;
    };
    personVueFun();
};

function personVueFun() {
    let personVue=new Vue({
        el:"#personVue",
        data:{
            userId:221,
            userSid:"0",
            userRealName:"",
            currentExam:9,
            score:0.0,
            buildCount:0,
            workTime:0,

            scoreDetail:[],

            personTimeDurationGraph:"",
            timeDurationGraphData:[],
            startTime:new Date().getTime(),

            allBuildPathInfo:[],

            showDurationStaDom:false,
            durationStatistics:"",
        },
        methods:{
            initialTimeDuration:function () {
                this.getTimeDurationData();
                setTimeout(function () {
                    this.personTimeDurationGraph=echarts.init(document.getElementById("timeDurationGraph"));
                    let option = {
                        tooltip: {
                            formatter: function (params) {
                                return params.marker
                                    + (params.value[3]/(60*60*1000)).toFixed(2)
                                    + ' h:'
                                    // + params.name + ': '
                                    +(personVue.transferLongToDateTimeString(params.value[1]))
                                    +"~"
                                    +(personVue.transferLongToDateTimeString(params.value[2]))
                                    ;
                            }
                        },
                        title: {
                            // text: "Student:["+personVue.userRealName+"("+personVue.userSid+")]的时间划分",
                            text: "Student:["+personVue.userId+"]的时间划分",
                            left: 'center'
                        },
                        dataZoom: [{
                            type: 'slider',
                            filterMode: 'weakFilter',
                            showDataShadow: false,
                            top: 400,
                            height: 20,
                            borderColor: 'transparent',
                            backgroundColor: '#e2e2e2',
                            handleIcon: 'M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7v-1.2h6.6z M13.3,22H6.7v-1.2h6.6z M13.3,19.6H6.7v-1.2h6.6z', // jshint ignore:line
                            handleSize: 20,
                            handleStyle: {
                                shadowBlur: 6,
                                shadowOffsetX: 1,
                                shadowOffsetY: 2,
                                shadowColor: '#aaa'
                            },
                            labelFormatter: ''
                        }, {
                            type: 'inside',
                            filterMode: 'weakFilter'
                        }],
                        grid: {
                            height:300
                        },
                        xAxis: {
                            min: this.startTime,
                            scale: true,
                            axisLabel: {
                                formatter: function (val) {
                                    // return personVue.transferLongToDateTimeString(val);
                                    return personVue.transferLongToDateString(val);
                                }
                            }
                        },
                        yAxis: {
                            data: []
                        },
                        series: [{
                            type: 'custom',
                            renderItem: personVue.renderItem,
                            itemStyle: {
                                normal: {
                                    opacity: 0.8
                                }
                            },
                            encode: {
                                x: [1, 2],
                                y: 0
                            },
                            data: personVue.timeDurationGraphData
                        }]
                    };
                    this.personTimeDurationGraph.setOption(option);
                    this.personTimeDurationGraph.on("dblclick",function (param) {
                        // console.log(param);
                        // console.log(param.name);
                        // console.log((param.value)[3]);
                        document.getElementById("timeDurationGraph").style.width=60+"%";
                        // $("#timeDurationGraph").style.width="60%";
                          personVue.personTimeDurationGraph.resize();
                            },400);

                        });
                        let formData=new FormData;
                        formData.append("durationId",param.name);
                        personVue.$http.post("/durationStatistics",formData).then(function (response) {
                            let responseData=response.data;
                            console.log(responseData);
                            if (responseData.result){
                                personVue.showDurationStaDom=true;
                                personVue.durationStatistics=responseData.response;
                                let insertTime=responseData.response.insertTime;
                                let debugTime=responseData.response.debugTime;
                                personVue.durationStatistics.insertTime=(insertTime/(60*1000)).toFixed(2);
                                personVue.durationStatistics.debugTime=(debugTime/(60*1000)).toFixed(2);
                                let percent=((insertTime+debugTime)/((param.value)[3])).toFixed(2);
                                personVue.durationStatistics.validTime=percent;

                                if (percent<0.3){
                                    personVue.durationStatistics.guess="思考";
                                } else if (percent>0.7){
                                    personVue.durationStatistics.guess="疯狂编码";
                                } else{
                                    personVue.durationStatistics.guess="边喝咖啡边写代码";
                                }
                            }else{
                                toastr.warning(responseData.response);
                            }

                        })
                    });
                },500);

            },
            renderItem:function (params, api) {
                let categoryIndex = api.value(0);
                let start = api.coord([api.value(1), categoryIndex]);
                let end = api.coord([api.value(2), categoryIndex]);
                let height = api.size([0, 1])[1] * 0.6;

                let rectShape = echarts.graphic.clipRectByRect({
                    x: start[0],
                    y: start[1] - height / 2,
                    width: end[0] - start[0],
                    height: height
                }, {
                    x: params.coordSys.x,
                    y: params.coordSys.y,
                    width: params.coordSys.width,
                    height: params.coordSys.height
                });

                return rectShape && {
                    type: 'rect',
                    shape: rectShape,
                    style: api.style()
                };
            },
            getTimeDurationData:function () {
                let formData=new FormData;
                formData.append("examId",this.currentExam);
                formData.append("userId",this.userId);
                this.$http.post("/personTimeDuration",formData).then(function (response) {
                    let responseData=response.data;
                    if (responseData.result==true){
                        // console.log(responseData);
                        let types = [
                            {name: this.userId, color: '#7b9ce1'},
                            {name: this.userId, color: '#bd6d6c'},
                            {name: this.userId, color: '#75d874'},
                            {name: this.userId, color: '#e0bc78'},
                            {name: this.userId, color: '#dc77dc'},
                            {name: this.userId, color: '#72b362'}
                        ];

                        // let headTime=responseData.response[0].beginTime;
                        // headTime=new Date(headTime);
                        // headTime=headTime.getFullYear()+"/"+(headTime.getMonth()+1)+"/"+headTime.getDay()+" 00:00:00";
                        // headTime=new Date(headTime).getTime();
                        // this.startTime=headTime;
                        this.startTime=responseData.response[0].beginTime;
                        this.startTime=new Date(this.startTime).getTime();

                        let allWorkTime=0;
                        for (let i = 0; i < responseData.response.length; i++) {
                            let baseTime = responseData.response[i].beginTime;
                            baseTime=new Date(baseTime).getTime();
                            let typeItem = types[i%(types.length)];
                            let duration =(responseData.response[i].duration);
                            allWorkTime=allWorkTime+duration;
                            duration=duration*60*1000;
                            this.timeDurationGraphData.push({
                                name: responseData.response[i].durationId,
                                value: [
                                    1,
                                    baseTime,
                                    baseTime += duration,
                                    duration
                                ],
                                itemStyle: {
                                    normal: {
                                        color: typeItem.color
                                    }
                                }
                            });
                            baseTime += 2000;
                        }

                        let allWorkHour=(allWorkTime/60.0).toFixed(2);
                        this.workTime=allWorkTime+"min("+allWorkHour+"h)";
                    }else{
                        toastr.warning(responseData.response);
                    }
                })
            },
            transferLongToDateString:function (longTypeDate){
                let date=new Date();
                date.setTime(longTypeDate);
                return (date).toLocaleDateString();
            },
            transferLongToDateTimeString:function (longTypeDate){
                let date=new Date();
                date.setTime(longTypeDate);
                return (date).pattern("yyyy-MM-dd HH:mm:ss");
            },
            getPersonExamScore:function () {
                let formData=new FormData;
                formData.append("examId",this.currentExam);
                formData.append("userId",this.userId);
                this.$http.post("/personScore",formData).then(function (response) {
                    let responseData=response.data;
                    if (responseData.result==true){
                        let resultResponse=responseData.response;
                        // console.log(resultResponse);
                        this.userId=resultResponse.userId;
                        this.userSid=resultResponse.userName;
                        this.userRealName=resultResponse.realName;
                        this.score=resultResponse.total_score;
                        this.scoreDetail=resultResponse.score_detail;
                    } else{
                        toastr.warning(responseData.response);
                    }
                })
            },
            getPersonExamBuildPath:function () {
                let formData=new FormData;
                formData.append("examId",this.currentExam);
                formData.append("userId",this.userId);
                this.$http.post("/personBuildPath",formData).then(function (response) {
                    let responseData=response.data;
                    if (responseData.result){
                        // console.log(responseData.response);
                        this.allBuildPathInfo=responseData.response;
                    } else{
                        toastr.warning(responseData.response);
                    }
                })
            }
        },
        mounted:function () {
            let urlStr = window.location.href; //获取访问地址
            let urlArr = urlStr.split("/personExam/"); //截取字符串，存到数组中
            let examIdAndUserId=urlArr[1];// 获取.com之前的字符串
            let paramArr=examIdAndUserId.split("/");
            this.currentExam = paramArr[0];
            this.userId=paramArr[1];
            this.getPersonExamScore();
            this.initialTimeDuration();
            this.getPersonExamBuildPath();
        }
    });

}