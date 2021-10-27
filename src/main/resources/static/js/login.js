var vm = new Vue({
    el:"#app",
    data:{
        isLogin:true,
        isRedirecting:false,
        loginForm:{
            userName:"",
            password:""
        },
        registerForm:{
            userName:"",
            realName:"",
            password:""
        },
        confirmPassword:"",
        loginTip:"",
        registerTip:""
    },
    created:function(){
        if(localStorage.getItem("token") != null)
            window.location.href = "../main.html";

    },
    methods:{
        switchForm(){
            this.loginTip = "";
            this.registerTip = "";
            this.isLogin = !this.isLogin;
        },

        inputFocus(){
            this.loginTip = "";
            this.registerTip = "";
        },
        
        login(){
            $.ajax({
                type: "POST",
                url: api_url+"user/login",
                data: this.loginForm,
                dataType: "json",
                success: function (res) {
                    console.log(res)
                    if(res.msg==="OK"){
                        localStorage.setItem("token",res.data.token);
                        localStorage.setItem("role",res.data.role);
                        vm.isRedirecting = true;
                        setTimeout(function(){
                            window.location.href="../main.html";
                        },1000)
                    }else if(res.msg==="WRONG"){
                        vm.login_tip("用户名或密码错误");
                    }else if(res.msg==="BLOCKED"){
                        vm.login_tip("用户已被冻结");
                    }
                },
                error: function(err){
                    vm.login_tip("网络或服务器错误");
                    console.log(err);
                }
            });
        },

        register(){
            $.ajax({
                type: "POST",
                url: api_url+"user/register",
                data: this.registerForm,
                dataType: "json",
                success: function (res) {
                    if(res.msg==="OK"){
                        vm.registerForm.userName="";
                        vm.registerForm.realName="";
                        vm.registerForm.password="";
                        vm.confirmPassword="";
                        vm.isRedirecting = true;
                        setTimeout(function(){
                            vm.isRedirecting = false;
                            vm.switchForm();
                        },1000)
                    }else{
                        vm.register_tip(res.data)
                    }
                },
                error: function(err){
                    vm.register_tip("网络或服务器错误");
                    console.log(err);
                }
            });
        },

        login_tip(str){
            this.loginTip = str;
        },

        register_tip(str){
            this.registerTip = str;
        }
    }
})

