var vm = new Vue({
    el:"#app",
    data:{
        isLogin:true,
        loginForm:{
            userName:"",
            password:""
        },
        registerForm:{
            userName:"",
            realName:"",
            password:""
        },
        confirmPassword:""
    },
    methods:{
        switchForm(){
            this.isLogin = !this.isLogin;
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
                    }else if(res.msg==="WRONG"){
                        console.log("用户名或密码错误")
                    }
                },
                fail: function(res){
                    console.log(res);
                }
            });
        },

        register(){
            $.ajax({
                type: "POST",
                url: api_url+"user/register",
                data: this.loginForm,
                dataType: "json",
                success: function (res) {
                    if(res.msg="OK"){
                        
                    }else if(res.msg="WRONG"){

                    }
                },
                fail: function(res){
                    console.log(res);
                }
            });
        }
    }
})

