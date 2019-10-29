window.onload=function () {
    init();
};

function init() {
    let graphVue=new Vue({
        el:"#graphVue",
        data:{
            dataTimer:"",
            renderTimer:"",

            timeGraphData:[],
            ageGraphData:[],
            genderGraphData:[],
            levelGraphData:[],
            regionGraphData:[],
            mapData:"",

            timeGraph:"",
            ageGraph:"",
            genderGraph:"",
            levelGraph:"",
            regionGraph:"",
        },
        methods:{
            renderGraph: function () {
                console.log("start rendering!");
                //time render
                this.timeGraph=echarts.init(document.getElementById("timeRangeGraph"));
                let timeOption = {
                        tooltip: {
                        },
                        title: {
                            text: "各时段评论人数分布",
                            left: 'center'
                        },
                    dataset: {
                        dimensions: ['time', 'number'],
                        source: this.timeGraphData
                    },
                        grid: {
                            height:300
                        },
                        xAxis: {
                            type: 'category'
                        },
                        yAxis: {
                        },
                        series: [{
                            type: 'bar'
                        }]
                    };
                this.timeGraph.setOption(timeOption);
                //age render
                var ageData=[];
                for(var i=0;i<this.ageGraphData.length;i++){
                    // if(this.ageGraphData[i].age<=1920||this.ageGraphData[i].age>==2020){
                    //     break;
                    // }
                    ageData[i]={
                        birthday: this.ageGraphData===0?"保密":JSON.stringify(this.ageGraphData[i].age),
                        number: this.ageGraphData[i].number
                    }
                }
                this.ageGraph=echarts.init(document.getElementById("ageDistribution"));
                let ageOption = {
                    tooltip: {
                    },
                    title: {
                        text: "用户出生日期分布",
                        left: 'center'
                    },
                    dataset: {
                        dimensions: ['birthday', 'number'],
                        source: ageData.sort( function (a,b) { return a.birthday - b.birthday})
                    },
                    grid: {
                        height:300
                    },
                    xAxis: {
                        type: 'category'
                    },
                    yAxis: {
                    },
                    series: [{
                        type: 'bar'
                    }]

                };
                this.ageGraph.setOption(ageOption);
                //gender render
                this.genderGraph=echarts.init(document.getElementById("genderDistribution"));
                var genderData=[];

                for (var i = 0; i < this.genderGraphData.length; i++) {
                    var gender="";
                    switch (this.genderGraphData[i].gender) {
                        case 0:
                            gender="保密";
                            break;
                        case 1:
                            gender="男";
                            break;
                        case 2:
                            gender="女";
                            break;
                    }
                    if(gender.length===0){
                        break;
                    }
                    genderData[i]={
                        name: gender,
                        value: this.genderGraphData[i].number
                    };
                }
                let genderOption = {
                    tooltip : {
                        trigger: 'item',
                        formatter: "{a} <br/>{b} : {c} ({d}%)"
                    },
                    title: {
                        text: "用户性别分布",
                        left: 'center'
                    },
                    legend: {
                        orient: 'vertical',
                        left: 'left',
                        data: ['男','女','保密']
                    },
                    series : [
                        {
                            name: '性别',
                            type: 'pie',
                            radius : '80%',
                            center: ['50%', '60%'],
                            data:genderData,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: 'rgba(0, 0, 0, 0.5)'
                                }
                            }
                        }
                    ]
                };
                this.genderGraph.setOption(genderOption);

                //level render
                var levelData=[];
                for(var i=0;i<this.levelGraphData.length;i++){
                    levelData[i]=this.levelGraphData[i].number;
                }
                this.levelGraph=echarts.init(document.getElementById("levelDistribution"));
                let levelOption = {
                    xAxis: {
                        type: 'category',
                        data: [0, 1, 2,3,4,5,6,7,8,9,10]
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: levelData,
                        type: 'line',
                        smooth: true
                    }]
                };
                this.levelGraph.setOption(levelOption);

                //region render
                var regionData=[];
                for(var i=0;i<this.regionGraphData.length;i++){
                    var code=JSON.stringify(this.regionGraphData[i].code);
                    regionData[i]={
                        name:this.mapData[code],
                        value: this.regionGraphData[i].number
                    };

                }
                console.log(regionData);
                this.regionGraph=echarts.init(document.getElementById("regionDistribution"));
                let regionOption = {
                    backgroundColor: '#FFFFFF',
                    title: {
                        text: '用户地区分布',
                        subtext: '',
                        x:'center'
                    },
                    tooltip : {
                        trigger: 'item'
                    },

                    //左侧小导航图标
                    visualMap: {
                        show : true,
                        x: 'left',
                        y: 'center',
                        splitList: [
                            {start: 500, end:600},{start: 400, end: 500},
                            {start: 300, end: 400},{start: 200, end: 300},
                            {start: 100, end: 200},{start: 0, end: 100},
                        ],
                        color: ['#5475f5', '#9feaa5', '#85daef','#74e2ca', '#e6ac53', '#9fb5ea']
                    },

                    //配置属性
                    series: [{
                        name: '数据',
                        type: 'map',
                        mapType: 'china',
                        roam: false,
                        label: {
                            normal: {
                                show: true  //省份名称
                            },
                            emphasis: {
                                show: false
                            }
                        },
                        data:regionData //数据
                    }]
                };
                this.regionGraph.setOption(regionOption);

                console.log("end rendering")

            },
            initial:function () {
                console.log("start init");
                this.getTimeGraphData();
                this.getAgeGraphData();
                this.getGenderGraphData();
                this.getLevelGraphData();
                this.getRegionGraphData();
                this.getMapData();
                console.log("Data init complete!");

            },
            getTimeGraphData:function () {
                this.$http.get("/statistic/time").then(function (response) {
                    this.timeGraphData=response.data;

                })
            },
            getAgeGraphData:function () {
                this.$http.get("/statistic/age").then(function (response) {
                    this.ageGraphData=response.data;

                })
            },
            getGenderGraphData:function () {
                this.$http.get("/statistic/gender").then(function (response) {
                    this.genderGraphData=response.data


                })
            },
            getLevelGraphData:function () {
                this.$http.get("/statistic/level").then(function (response) {
                    this.levelGraphData=response.data;

                })
            },
            getRegionGraphData:function () {
                this.$http.get("/statistic/province").then(function (response) {
                    this.regionGraphData=response.data;

                })
            },
            getMapData:function () {
                this.$http.get("/static/js/code_address_list.json").then(function (response) {
                    this.mapData=response.data;

                })
            },

        },
        mounted:function () {

            this.dataTimer=setInterval(this.initial,5000);
            this.renderTimer=setInterval(this.renderGraph,5000);

            // this.initial();
            // this.renderGraph()
        }
    });

}
