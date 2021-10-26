var vm = new Vue({
    el:"#app",
    data:{
        role:0
    },
    created:function(){
        if(localStorage.getItem("token") == null)
            window.location.href = "../login.html";
        this.role=localStorage.getItem("role");
    },
    methods:{
        toPage(url){
            document.getElementById("info").src=url;
        },
        logout(){
            localStorage.removeItem("token");
            localStorage.removeItem("role");
            window.location.href = "../login.html";
        }
    }
})