window.onload=function () {
    init();
};

function init() {
    let commentVue=new Vue({
        el:"#commentVue",
        data:{
            dataTimer:"",
            renderTimer:"",

            graph1Data:[],
            graph1Edge:[],
            graph2Data:[],
            graph2Edge:[],
            graph3Data:[],
            graph3Edge:[],
            graph4Data:[],
            graph4Edge:[],

            graph1:"",
            graph2:"",
            graph3:"",
            graph4:"",
        },
        methods:{
            renderGraph: function () {
                console.log("start rendering!");

                //graph1 render
                var graph1DataSet=["user","song","playlist"];
                this.graph1=echarts.init(document.getElementById("graph1"));
                this.graph1Data.forEach(function (node) {
                    node.itemStyle = null;
                    node.symbolSize = 10;
                    node.value = node.symbolSize;
                    node.category = 0;
                    // Use random x, y
                    node.x = node.y = null;
                    node.draggable = true;
                });
                this.graph1Edge.forEach(function (edge) {
                   edge.source=edge.srcId;
                   edge.target=edge.dstId;
                });
                console.log()
                let graph1Option = {
                    title: {
                        text: '评论',
                        subtext: '网易云',
                        top: 'top',
                        left: 'center'
                    },
                    tooltip: {},
                    legend: [{
                        // selectedMode: 'single',
                        data: graph1DataSet.map(function (a) {
                            return a;
                        })
                    }],
                    animation: false,
                    series : [
                        {
                            name: '评论',
                            type: 'graph',
                            layout: 'force',
                            data: this.graph1Data,
                            links: this.graph1Edge,
                            categories: graph1DataSet,
                            roam: true,
                            label: {
                                normal: {
                                    position: 'right'
                                }
                            },
                            force: {
                                repulsion: 100
                            }
                        }
                    ]
                };
                this.graph1.setOption(graph1Option);
                //graph2 render
                var graph2DataSet=[];
                this.graph2=echarts.init(document.getElementById("graph2"));
                let graph2Option = {
                    title: {
                        text: '评论',
                        subtext: '网易云',
                        top: 'top',
                        left: 'center'
                    },
                    tooltip: {},
                    legend: [{
                        // selectedMode: 'single',
                        data: graph1DataSet.map(function (a) {
                            return a;
                        })
                    }],
                    animation: false,
                    series : [
                        {
                            name: '评论',
                            type: 'graph',
                            layout: 'force',
                            data: this.graph1Data,
                            links: this.graph1Edge,
                            categories: graph1DataSet,
                            roam: true,
                            label: {
                                normal: {
                                    position: 'right'
                                }
                            },
                            force: {
                                repulsion: 100
                            }
                        }
                    ]
                };
                this.graph2.setOption(graph2Option);
                //graph3 render
                var graph3DataSet=[];
                this.graph3=echarts.init(document.getElementById("graph3"));
                let graph3Option = {
                    title: {
                        text: '评论',
                        subtext: '网易云',
                        top: 'top',
                        left: 'center'
                    },
                    tooltip: {},
                    legend: [{
                        // selectedMode: 'single',
                        data: graph1DataSet.map(function (a) {
                            return a;
                        })
                    }],
                    animation: false,
                    series : [
                        {
                            name: '评论',
                            type: 'graph',
                            layout: 'force',
                            data: this.graph1Data,
                            links: this.graph1Edge,
                            categories: graph1DataSet,
                            roam: true,
                            label: {
                                normal: {
                                    position: 'right'
                                }
                            },
                            force: {
                                repulsion: 100
                            }
                        }
                    ]
                };
                this.graph3.setOption(graph3Option);
                //graph4 render
                var graph4DataSet=[];
                this.graph4=echarts.init(document.getElementById("graph4"));
                let graph4Option = {
                    title: {
                        text: '评论',
                        subtext: '网易云',
                        top: 'top',
                        left: 'center'
                    },
                    tooltip: {},
                    legend: [{
                        // selectedMode: 'single',
                        data: graph1DataSet.map(function (a) {
                            return a;
                        })
                    }],
                    animation: false,
                    series : [
                        {
                            name: '评论',
                            type: 'graph',
                            layout: 'force',
                            data: this.graph1Data,
                            links: this.graph1Edge,
                            categories: graph1DataSet,
                            roam: true,
                            label: {
                                normal: {
                                    position: 'right'
                                }
                            },
                            force: {
                                repulsion: 100
                            }
                        }
                    ]
                };
                this.graph4.setOption(graph4Option);


                console.log("end rendering")

            },
            initial:function () {
                console.log("start init");
                this.getGraph1Data();
                this.getGraph2Data();
                this.getGraph3Data();
                this.getGraph4Data();
                console.log("Data init complete!");

            },
            getGraph1Data:function () {
                this.$http.get("/graph/getAddNodes").then(function (response) {
                    this.graph1Data=response.data;
                });
                this.$http.get("/graph/getAddEdges").then(function (response) {
                    this.graph1Edge=response.data;
                })
            },
            getGraph2Data:function () {
                this.$http.get("/graph/getTopSongNodes").then(function (response) {
                    this.graph2Data=response.data;

                });
                this.$http.get("/graph/getTopSongEdges").then(function (response) {
                    this.graph2Edge=response.data;
                });
            },
            getGraph3Data:function () {
                this.$http.get("/graph/getTopListNodes").then(function (response) {
                    this.graph3Data=response.data
                });
                this.$http.get("/graph/getTopListEdges").then(function (response) {
                    this.graph3Edge=response.data
                });
            },
            getGraph4Data:function () {

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
