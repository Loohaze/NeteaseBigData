window.onload=function () {
    init();
};

function init() {
    let commentVue=new Vue({
        el:"#commentVue",
        data:{
            dataTimer:"",
            renderTimer:"",

            categories:[
                {
                    name:"playlist"
                },
                {
                    name:"user"
                },
                {
                    name:"song"
                }],
            graph1Data:[],
            graph1Edge:[],
            graph2Data:[],
            graph2Edge:[],


            graph1:"",
            graph2:"",

        },
        methods:{
            renderGraph: function () {
                console.log("start rendering!");

                //graph1 render
                this.graph1=echarts.init(document.getElementById("graph1"));
                this.graph1Data.forEach(function (node) {
                    node.id=JSON.stringify(node.nodeId);
                    node.itemStyle = null;
                    node.value = node.symbolSize;
                    switch (node.prop) {
                        case 'playlist':
                            // code
                            node.category=0;
                            node.symbolSize=10;
                            break;
                        case 'user':
                            // code
                            node.category=1;
                            node.symbolSize=20;
                            break;
                        case 'song':
                            // code
                            node.category=2;
                            node.symbolSize=30;
                            break;
                    }
                    // Use random x, y
                    node.x = node.y = null;
                    node.draggable = false;
                });
                var id=0;
                this.graph1Edge.forEach(function (edge) {
                    edge.id=JSON.stringify(id);
                    edge.source=JSON.stringify(edge.srcId);
                    edge.target=JSON.stringify(edge.dstId);
                    id+=1;
                });
                console.log(this.graph1Data)
                console.log(this.graph1Edge)
                let graph1Option = {
                    title: {
                        text: 'top歌曲-歌单',
                        subtext: '有向关系图',
                        top: 'top',
                        left: 'center'
                    },
                    tooltip: {},
                    legend: [{
                        // selectedMode: 'single',
                        data: this.categories.map(function (a) {
                            return a.name;
                        }),
                        top: 'bottom',
                        left: 'right'
                    }],
                    animation: false,
                    series : [
                        {
                            name: '',
                            type: 'graph',
                            layout: 'force',
                            data: this.graph1Data,
                            links: this.graph1Edge,
                            categories: this.categories,
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
                this.graph2=echarts.init(document.getElementById("graph2"));
                this.graph2Data.forEach(function (node) {
                    node.id=JSON.stringify(node.nodeId);
                    node.itemStyle = null;
                    node.symbolSize = 10;
                    node.value = node.symbolSize;
                    switch (node.prop) {
                        case 'playlist':
                            // code
                            node.category=0;
                            node.symbolSize=30;
                            break;
                        case 'user':
                            // code
                            node.category=1;
                            node.symbolSize=20;
                            break;
                        case 'song':
                            // code
                            node.category=2;
                            node.symbolSize=10;
                            break;
                    }
                    // Use random x, y
                    node.x = node.y = null;
                    node.draggable = false;
                });
                var id=0;
                this.graph2Edge.forEach(function (edge) {
                    edge.id=JSON.stringify(id);
                    edge.source=JSON.stringify(edge.srcId);
                    edge.target=JSON.stringify(edge.dstId);
                    id+=1;
                });

                let graph2Option = {
                    title: {
                        text: 'top歌单-歌曲',
                        subtext: '有向关系图',
                        top: 'top',
                        left: 'center'
                    },
                    tooltip: {},
                    legend: [{
                        // selectedMode: 'single',
                        data: this.categories.map(function (a) {
                            return a.name;
                        }),
                        top: 'bottom',
                        left: 'right'
                    }],
                    animation: false,
                    series : [
                        {
                            name: '',
                            type: 'graph',
                            layout: 'force',
                            data: this.graph2Data,
                            links: this.graph2Edge,
                            categories: this.categories,
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

                console.log("end rendering")

            },
            initial:function () {
                console.log("start init");
                this.getGraph1Data();
                this.getGraph2Data();
                console.log("Data init complete!");

            },
            getGraph1Data:function () {
                this.$http.get("/graph/getTopSongNodes").then(function (response) {
                    this.graph1Data=response.data;
                });
                this.$http.get("/graph/getTopSongEdges").then(function (response) {
                    this.graph1Edge=response.data;
                });


            },
            getGraph2Data:function () {
                this.$http.get("/graph/getTopListNodes").then(function (response) {
                    this.graph2Data=response.data;

                });
                this.$http.get("/graph/getTopListEdges").then(function (response) {
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

        },
        mounted:function () {

            // this.dataTimer=setInterval(this.initial,5000);
            // this.renderTimer=setInterval(this.renderGraph,5000);

            this.initial();
            // this.renderGraph();
            this.renderTimer=setTimeout(this.renderGraph,10000);
        }
    });

}
