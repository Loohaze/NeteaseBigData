window.onload=function () {
    MLLibVueFun();
};

function MLLibVueFun() {
    let MLLibVue=new Vue({
        el:"#MLShowVue",
        data:{
            partUserDataTable:"",
            generalShowChart:"",
            userRecommendSongsChart:"",
        },
        methods:{
            initialPartUserDataTable:function () {
                this.partUserDataTable=$("#partUserDataTable").bootstrapTable("destroy").bootstrapTable({
                    toolbar: '#toolbar',                //工具按钮用哪个容器
                    striped: true,                      //是否显示行间隔色
                    cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true,                   //是否显示分页（*）
                    sortable: true,                     //是否启用排序
                    sortOrder: "asc",                   //排序方式
                    sidePagination: "client",           //分页方式：client客户端分页，server服务端分页（*）
                    paginationLoop:true,                //分页条无限循环
                    pageNumber: 1,                       //初始化加载第一页，默认第一页
                    pageSize: 10,                       //每页的记录行数（*）
                    pageList: [10,20,40],        //可供选择的每页的行数（*）
                    search: true,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
                    strictSearch: false,
                    showColumns: true,                  //是否显示所有的列
                    showRefresh: false,                  //是否显示刷新按钮
                    showFullscreen:false,
                    minimumCountColumns: 2,             //最少允许的列数
                    clickToSelect: true,                //是否启用点击选中行
                    // height: 400,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
                    uniqueId: "question",                     //每一行的唯一标识，一般为主键列
                    showToggle: false,                    //是否显示详细视图和列表视图的切换按钮
                    cardView: false,                    //是否显示详细视图
                    showLoading: true,
                    undefinedText: 0,
                    silentSort: false,                 //显示排序时候的等待文字
                    showExport: true,
                    exportDataType: 'all',
                    virtualScroll:true,
                    showExtendedPagination:true,
                    // buttonsAlign:"left",     //工具栏按钮的位置
                    selectItemName:"question",  //选择框元素对应的name名称
                    columns: [
                        {
                            field: 'userName',
                            title: '用户昵称',
                            sortable:true,
                        },
                        {
                            field: 'level',
                            title: '用户等级',
                            sortable:true,
                        },
                        {
                            field: 'gender',
                            title: '性别',
                            sortable:true,
                        },
                        {
                            field: 'birthday',
                            title: '生日',
                            sortable:true,
                        },
                    ],
                    onDblClickRow:function (row, $element, field) {
                        MLLibVue.getUserRecommendSong(row.userId,row.userName)
                        // console.log(row.userId);
                    },
                });
            },
            initialGeneralShowChart:function(){
                this.generalShowChart=echarts.init(document.getElementById('generalShowChart'));
                this.generalShowChart.setOption({
                    title: {
                        text: '高频热词'
                    },
                    tooltip: {
                        trigger: 'item'
                    },
                    // legend: {
                    //     data: categories,
                    // },
                    grid: {
                        left: '3%',
                        right: '4%',
                        bottom: '3%',
                        containLabel: true
                    },
                    series: [{
                        type: 'graph',
                    }],
                });
                this.generalShowChart.showLoading();
            },
            initialUserRecommendSongsChart:function(){
              this.userRecommendSongsChart=  echarts.init(document.getElementById('userRecommendSongsChart'));
              this.userRecommendSongsChart.setOption({
                  title : {
                      text: '用户歌曲推荐',
                      subtext: '前10',
                      x:'center'
                  },
                  tooltip : {
                      trigger: 'item',
                      formatter: "{a} <br/>{b} : {c} ({d}%)"
                  },
                  legend: {
                      x : 'center',
                      y : 'bottom',
                      data:[]
                  },
                  toolbox: {
                      show : true,
                      feature : {
                          mark : {show: true},
                          dataView : {show: true, readOnly: false},
                          magicType : {
                              show: true,
                              type: ['pie', 'funnel']
                          },
                          restore : {show: true},
                          saveAsImage : {show: true}
                      }
                  },
                  calculable : true,
                  series : [

                      {
                          name:'面积模式',
                          type:'pie',
                          radius : [30, 110],
                          center : ['75%', '50%'],
                          roseType : 'area',
                          data:[]
                      }
                  ]
              });
              this.userRecommendSongsChart.showLoading();
            },
            getUserInfoTable:function () {
                this.initialPartUserDataTable();
                this.partUserDataTable.bootstrapTable("showLoading");
                this.$http.get("/ml/Users").then(function (response) {
                    let responseData=response.data;

                    this.partUserDataTable.bootstrapTable("load",responseData);
                    this.partUserDataTable.bootstrapTable("refresh");
                    this.partUserDataTable.bootstrapTable("hideLoading");
                })
            },
            getWordsFrequency:function () {
                this.initialGeneralShowChart();
                this.$http.get().then(function (response) {
                    let responseData=response.data;

                    MLLibVue.generalShowChart.hideLoading();
                    console.log("开始重绘总览图");

                    //避免卡顿，只绘制前70个关键词
                    let wordsArray = [];
                    for(let i=0;i<50;i++){
                        let item={
                            name: responseData[i],
                            value: responseData[i],
                        }
                        wordsArray.push(item);
                    }

                    MLLibVue.generalShowChart.setOption({

                        series:[
                            {name:'keyword',
                                type:'graph',
                                layout: 'force',
                                data:wordsArray,
                                roam:true,
                                symbolSize: function (data) {
                                    // console.log(data);
                                    return Math.round(15 + data * 80 / authorNum[0]);
                                },
                                label: {
                                    normal: {
                                        show: true
                                    },
                                    emphasis: {
                                        show: true,
                                        formatter: function (param) {
                                            return param.name;
                                        },
                                        color: 'black'
                                    }
                                },
                                force:{
                                    repulsion: 65
                                }
                            },
                        ]
                    });
                })
            },
            getUserRecommendSong:function (userId,userName) {
                console.log(userId+";"+userName);
                this.initialUserRecommendSongsChart();
                let formData=new FormData;
                formData.append("userId",userId);
                this.$http.post("/ml/rec",formData).then(function (response) {
                    let responseData=response.data;
                    let allRecommendRaw=[];
                    let allSongs=[];
                    let allSongAndRatioData=[];
                    for (let i=0;i<responseData.length;i++){

                        let tmpFormData=new FormData;
                        tmpFormData.append("songId",parseInt(responseData[i].recs[1]));
                        MLLibVue.$http.post("/ml/Song",tmpFormData).then(function (res) {
                            console.log(res);
                            let songData=res.data;
                            let recDict={"songId":parseInt(responseData[i].recs[1]),
                                "ratio":responseData[i].recs[2],
                                "songName":songData.songName};
                            allRecommendRaw.push(recDict);
                            allSongs.push(songData.songName);
                            allSongAndRatioData.push(responseData[i].recs[2]);
                        });

                    }
                    console.log(allSongs);

                    if (allSongs.length==responseData.length){
                        MLLibVue.userRecommendSongsChart.hideLoading();
                        MLLibVue.userRecommendSongsChart.setOption({
                            title : {
                                text: userName+'--歌曲推荐',
                                subtext: '前10',
                                x:'center'
                            },
                            tooltip : {
                                trigger: 'item',
                                formatter: "{a} <br/>{b} : {c} ({d}%)"
                            },
                            legend: {
                                x : 'center',
                                y : 'bottom',
                                data:allSongs
                            },
                            toolbox: {
                                show : true,
                                feature : {
                                    mark : {show: true},
                                    dataView : {show: true, readOnly: false},
                                    magicType : {
                                        show: true,
                                        type: ['pie', 'funnel']
                                    },
                                    restore : {show: true},
                                    saveAsImage : {show: true}
                                }
                            },
                            calculable : true,
                            series : [

                                {
                                    name:'面积模式',
                                    type:'pie',
                                    radius : [30, 110],
                                    center : ['75%', '50%'],
                                    roseType : 'area',
                                    data:allSongAndRatioData
                                }
                            ]
                        })
                    }

                })
            },
        },
        mounted:function () {
            this.getUserInfoTable();
            this.getWordsFrequency();
        }
    })
}