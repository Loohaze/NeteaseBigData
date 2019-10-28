window.onload=function () {
    init();
};

function init() {
    let graphVue=new Vue({
        el:"#graphVue",
        data:{
            timeGraphData:[],
            ageGraphData:[],
            genderGraphData:[],
            levelGraphData:[],
            regionGraphData:[],

            timeGraph:"",
            ageGraph:"",
            genderGraph:"",
            levelGraph:"",
            regionGraph:"",
        },
        methods:{
            initial:function () {
                this.getTimeGraphData();
                this.getAgeGraphData();
                this.getGenderGraphData();
                this.getLevelGraphData();
                this.getRegionGraphData();
                setTimeout(function () {
                    this.timeGraph=echarts.init(document.getElementById("timeRangeGraph"));
                    let timeOption = {
                        tooltip: {
                            formatter: function (params) {
                                return params;
                            }
                        },
                        title: {
                            text: "各时段评论人数分布",
                            left: 'center'
                        },
                        grid: {
                            height:300
                        },
                        xAxis: {
                            data: ["0:00","1:00"]
                        },
                        yAxis: {
                            data: []
                        },
                        series: [{
                            name:"销量",
                            type: 'bar',
                            data: graphVue.timeGraphData
                        }]
                    };
                    this.timeGraph.setOption(timeOption);
                },500);

            },
            getTimeGraphData:function () {
                this.$http.get("/").then(function (response) {
                    response.data=[{"time":"0:00","number":123},{"time":"1:00","number":456}];
                    this.timeGraphData=response.data;

                })
            },
            getAgeGraphData:function () {
                this.$http.get("/").then(function (response) {
                    this.ageGraphData=response.data;

                })
            },
            getGenderGraphData:function () {
                this.$http.get("/").then(function (response) {
                    this.genderGraphData=response.data;

                })
            },
            getLevelGraphData:function () {
                this.$http.get("/").then(function (response) {
                    this.levelGraphData=response.data;

                })
            },
            getRegionGraphData:function () {
                this.$http.get("/").then(function (response) {
                    this.regionGraphData=response.data;

                })
            },

        },
        mounted:function () {

            this.initial();
        }
    });

}
