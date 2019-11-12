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
                let songOption = {
                    tooltip: {
                    },
                    title: {
                        text: "歌曲排行",
                        left: 'center'
                    },
                    dataset: {
                        dimensions: ['name', 'rank'],
                        source: this.songGraphData
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
                this.songGraph.setOption(songOption);

                //playlist render
                this.playlistGraph=echarts.init(document.getElementById("playlist"));
                let playlistOption = {
                    tooltip: {
                    },
                    title: {
                        text: "歌单排行",
                        left: 'center'
                    },
                    dataset: {
                        dimensions: ['name', 'rank'],
                        source: this.playlistGraphData
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
                this.playlistGraph.setOption(playlistOption);

                //level render
                this.levelGraph=echarts.init(document.getElementById("level"));
                var levelData=[];

                let levelOption = {
                    tooltip: {
                    },
                    title: {
                        text: "等级排行",
                        left: 'center'
                    },
                    dataset: {
                        dimensions: ['name', 'level'],
                        source: this.levelGraphData
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
                this.levelGraph.setOption(levelOption);

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
