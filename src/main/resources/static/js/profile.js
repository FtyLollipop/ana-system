var vm = new Vue({
    el:"#app",
    data:{
        role:0,
        stateFilter:"all",
        isShowPopupForm:false,
        isEditingName:false,
        popupFormTips:"",
        userDetails:{
            role:0,
        },
        popupForm:{
            realName:"",
            password:"",
            confirmPassword:""
        }
    },
    created:function(){
        this.getUser();
    },
    methods:{
        getUser(){
            $.ajax({
                type: "GET",
                url: api_url+"user/getUser",
                headers: {
                    "token": localStorage.getItem("token")
                },
                success: function (res) {
                    console.log(res)
                    if(res.msg==="OK"){
                        vm.userDetails = res.data;
                    }else if(res.msg==="TOKEN_INVALID"){
                        window.parent.vm.logout();
                    }
                },
                error: function(err){
                    console.log(err);
                }
            });
        },
        showPopupForm(){
            this.popupFormTips = "";
            this.isShowPopupForm = true;
        },
        hidePopupForm(){
            this.isShowPopupForm = false;
        },
        clearPopupForm(){
            this.popupForm.title="";
            this.popupForm.content="";
            this.popupForm.state="";
            this.popupFormTips = "";
        },
        popupFormFocus(){
            this.popupFormTips = "";
        },
        editRealName(){
            this.popupForm.realName = this.userDetails.realName;
            this.isEditingName=true;
            this.showPopupForm();
        },
        editPassword(){
            this.popupForm.realName = this.userDetails.realName;
            this.isEditingName=false;
            this.showPopupForm();
        },
        submitRealName(){
            $.ajax({
                type: "POST",
                url: api_url+"user/changeRealName",
                headers: {
                    "token": localStorage.getItem("token")
                },
                data: {
                    realName: vm.popupForm.realName,
                },
                success: function (res) {
                    console.log(res)
                    if(res.msg==="OK"){
                        vm.hidePopupForm();
                        vm.getUser();
                    }else if(res.msg==="TOKEN_INVALID"){
                        window.parent.vm.logout();
                    }else{
                        vm.popupFormTips = res.data;
                    }
                },
                error: function(err){
                    console.log(err);
                }
            });
        },
        submitPassword(){
            if(this.popupForm.password != this.popupForm.confirmPassword){
                this.popupFormTips = "两次输入的密码不一致";
                return;
            }
            $.ajax({
                type: "POST",
                url: api_url+"user/changePassword",
                headers: {
                    "token": localStorage.getItem("token")
                },
                data: {
                    password: vm.popupForm.password
                },
                success: function (res) {
                    console.log(res)
                    if(res.msg==="OK"){
                        vm.hidePopupForm();
                        vm.getUser();
                    }else if(res.msg==="TOKEN_INVALID"){
                        window.parent.vm.logout();
                    }else{
                        vm.popupFormTips = res.data;
                    }
                },
                error: function(err){
                    console.log(err);
                }
            });
        },
        showDetails(index){
            this.popupForm = this.formList[index];
            this.currentShowIndex = index;
            this.showPopupForm(0);
        },
        deleteForm(){
            $.ajax({
                type: "DELETE",
                url: api_url+"user/deleteUser",
                headers: {
                    "token": localStorage.getItem("token")
                },
                data: {
                    userId: vm.formList[vm.currentShowIndex].userId,
                },
                success: function (res) {
                    console.log(res)
                    if(res.msg==="OK"){
                        vm.hidePopupForm();
                        vm.getForms(vm.currentPage);
                    }else if(res.msg==="TOKEN_INVALID"){
                        window.parent.vm.logout();
                    }
                },
                error: function(err){
                    console.log(err);
                }
            });
        }
    }
})