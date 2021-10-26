var vm = new Vue({
    el:"#app",
    data:{
        role:2
    },
    methods:{
        toPage(url){
            document.getElementById("info").src=url;
        }
    }
})