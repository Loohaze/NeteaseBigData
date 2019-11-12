window.onload=function () {
    init();
};

function init() {
    let ranksVue=new Vue({
        el:"#ranksVue",
        data:{
            dataTimer:"",
            renderTimer:"",

            songGraphData:[],
            playlistGraphData:[],
            levelGraphData:[],

            songGraph:"",
            playlistGraph:"",
            levelGraph:"",
        },
        methods:{
            renderGraph: function () {
                console.log("start rendering!");
                //song render
                this.songGraph=echarts.init(document.getElementById("song"));
                var myColor = ['#eb2100', '#eb3600', '#d0570e', '#d0a00e', '#34da62', '#00e9db', '#00c0e9', '#0096f3', '#33CCFF', '#33FFCC'];
                var name1=[];
                var rank1=[];
                // for(var i=0;i<this.songGraphData.length;i++){
                for(var i=0;i<50;i++){
                    name1[i]=this.songGraphData[i].name;
                    rank1[i]=this.songGraphData[i].rank;
                }

                name1.reverse();
                rank1.reverse();

                let option = {
                    backgroundColor: '#0e2147',
                    grid: {
                        left: '25%',
                        top: '12%',
                        right: '25%',
                        bottom: '8%',
                        containLabel: true
                    },
                    xAxis: [{
                        show: false,
                    }],
                    yAxis: [{
                        axisTick: 'none',
                        axisLine: 'none',
                        offset: '27',
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff',
                                fontSize: '16',
                            },
                            interval: 0
                        },
                        data: name1
                    }, {
                        name: '歌曲排行',
                        nameGap: '30',
                        nameTextStyle: {
                            color: '#ffffff',
                            fontSize: '30',
                        },
                        axisLine: {
                            lineStyle: {
                                color: 'rgba(0,0,0,0)'
                            }
                        },
                        data: [],
                    }],
                    series: [{
                        name: '条',
                        type: 'bar',
                        yAxisIndex: 0,
                        data: rank1,
                        label: {
                            normal: {
                                show: true,
                                position: 'right',
                                textStyle: {
                                    color: '#ffffff',
                                    fontSize: '16',
                                }
                            }
                        },
                        barWidth: 12,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var num = myColor.length;
                                    return myColor[params.dataIndex % num]
                                },
                            }
                        },
                        z: 2
                    }]
                };

                this.songGraph.setOption(option);

                //playlist render
                this.playlistGraph=echarts.init(document.getElementById("playlist"));
                var name2=[];
                var rank2=[];
                // for(var i=0;i<this.playlistGraphData.length;i++){
                for(var i=0;i<50;i++){
                    name2[i]=this.playlistGraphData[i].name;
                    rank2[i]=this.playlistGraphData[i].rank;
                }
                name2.reverse();
                rank2.reverse();
                let option2 = {
                    backgroundColor: '#0e2147',
                    grid: {
                        left: '25%',
                        top: '12%',
                        right: '25%',
                        bottom: '8%',
                        containLabel: true
                    },
                    xAxis: [{
                        show: false,
                    }],
                    yAxis: [{
                        axisTick: 'none',
                        axisLine: 'none',
                        offset: '27',
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff',
                                fontSize: '16',
                            },
                            interval: 0
                        },
                        data: name2
                    }, {
                        name: '歌单排行',
                        nameGap: '30',
                        nameTextStyle: {
                            color: '#ffffff',
                            fontSize: '30',
                        },
                        axisLine: {
                            lineStyle: {
                                color: 'rgba(0,0,0,0)'
                            }
                        },
                        data: [],
                    }],
                    series: [{
                        name: '条',
                        type: 'bar',
                        yAxisIndex: 0,
                        data: rank2,
                        label: {
                            normal: {
                                show: true,
                                position: 'right',
                                textStyle: {
                                    color: '#ffffff',
                                    fontSize: '16',
                                }
                            }
                        },
                        barWidth: 12,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var num = myColor.length;
                                    return myColor[params.dataIndex % num]
                                },
                            }
                        },
                        z: 2
                    }]
                };
                this.playlistGraph.setOption(option2);

                //level render
                this.levelGraph=echarts.init(document.getElementById("level"));

                var name3=[];
                var rank3=[];
                for(var i=0;i<this.levelGraphData.length;i++){
                // for(var i=0;i<50;i++){
                    name3[i]=this.levelGraphData[i].name;
                    rank3[i]=this.levelGraphData[i].level;
                }
                name3.reverse();
                rank3.reverse();
                let option3 = {
                    backgroundColor: '#0e2147',
                    grid: {
                        left: '25%',
                        top: '12%',
                        right: '25%',
                        bottom: '8%',
                        containLabel: true
                    },
                    xAxis: [{
                        show: false,
                    }],
                    yAxis: [{
                        axisTick: 'none',
                        axisLine: 'none',
                        offset: '27',
                        axisLabel: {
                            textStyle: {
                                color: '#ffffff',
                                fontSize: '16',
                            },
                            interval: 0
                        },
                        data: name3
                    }, {
                        name: '等级排行',
                        nameGap: '30',
                        nameTextStyle: {
                            color: '#ffffff',
                            fontSize: '30',
                        },
                        axisLine: {
                            lineStyle: {
                                color: 'rgba(0,0,0,0)'
                            }
                        },
                        data: [],
                    }],
                    series: [{
                        name: '条',
                        type: 'bar',
                        yAxisIndex: 0,
                        data: rank3,
                        label: {
                            normal: {
                                show: true,
                                position: 'right',
                                textStyle: {
                                    color: '#ffffff',
                                    fontSize: '16',
                                }
                            }
                        },
                        barWidth: 12,
                        itemStyle: {
                            normal: {
                                color: function(params) {
                                    var num = myColor.length;
                                    return myColor[params.dataIndex % num]
                                },
                            }
                        },
                        z: 2
                    }]
                };
                this.levelGraph.setOption(option3);

                console.log("end rendering")

            },
            initial:function () {
                console.log("start init");
                this.getSongRanksData();
                this.getPlaylistRanksData();
                this.getLevelRanksData();

                console.log("Data init complete!");

            },
            getSongRanksData:function () {
                this.$http.get("/graph/getSongRanks").then(function (response) {
                    this.songGraphData=response.data;

                })
            },
            getPlaylistRanksData:function () {
                this.$http.get("/graph/getPlaylistRanks").then(function (response) {
                    this.playlistGraphData=response.data;

                })
            },
            getLevelRanksData:function () {
                this.$http.get("/graph/getAvgLevelRanks").then(function (response) {
                    this.levelGraphData=response.data;
                })
            }
        },
        mounted:function () {

            // this.dataTimer=setInterval(this.initial,5000);
            // this.renderTimer=setInterval(this.renderGraph,5000);

            this.initial();
            this.renderTimer=setTimeout(this.renderGraph,5000);
        }
    });

}
