var vm = new Vue({
    el:"#app",
    data:{
        isLogin:true
    },
    methods:{
        switchForm(){
            this.isLogin = !this.isLogin;
        }
    }
})

