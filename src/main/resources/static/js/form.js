var vm = new Vue({
    el:"#app",
    data:{
        role:0,
        stateFilter:"all",
        isShowPopupForm:false,
        isPopupFormEditing:false,
        popupFormTips:"",
        currentShowIndex:0,
        currentPage:1,
        totalPage:0,
        formList:[
        ],
        popupForm:{
            formId:"1",
            realName:"",
            title:"",
            content:"",
            createTime:"",
            state:"",
            approveTime:"",
        }
    },
    computed:{
        isPrevDisabled:function(){
            return this.currentPage <= 1 ? true : false;
        },
        isNextDisabled:function(){
            return this.currentPage >= this.totalPage ? true : false;
        }
    },
    watch:{
        totalPage:function(val){
            this.currentPage = this.currentPage > val ? val : this.currentPage;
        },
        currentPage:function(){
            this.getForms(this.currentPage,this.stateFilter);
        },
        stateFilter:function(){
            this.getForms(this.currentPage,this.stateFilter);
        }
    },
    created:function(){
        this.getForms(1);
    },
    methods:{
        toPage(p){
            this.currentPage = p;
        },
        prev(){
            this.toPage(--this.currentPage);
        },
        next(){
            this.toPage(++this.currentPage);
        },
        getForms(page){
            let url = this.stateFilter == "all"? "form/getAdminForms?page=" : "form/getAdminFormsByState?state="+this.stateFilter+"&page="
            $.ajax({
                type: "GET",
                url: api_url+url+page,
                headers: {
                    "token": localStorage.getItem("token")
                },
                success: function (res) {
                    console.log(res)
                    if(res.msg==="OK"){
                        vm.formList = res.data.list;
                        vm.totalPage = res.data.total;
                    }else if(res.msg==="TOKEN_INVALID"){
                        window.parent.vm.logout();
                    }
                },
                error: function(err){
                    console.log(err);
                }
            });
        },
        showPopupForm(mode){
            this.isPopupFormEditing = mode == 0?false:true;
            if(mode == 1){
                this.clearPopupForm();
            }
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
        editForm(){
            this.isPopupFormEditing = true;
        },
        submitForm(){
            $.ajax({
                type: "POST",
                url: api_url+"form/editForm",
                headers: {
                    "token": localStorage.getItem("token")
                },
                data: {
                    formId: vm.popupForm.formId,
                    title: vm.popupForm.title,
                    content: vm.popupForm.content,
                    state: vm.popupForm.state
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
        },
        showDetails(index){
            this.popupForm = this.formList[index];
            this.currentShowIndex = index;
            this.showPopupForm(0);
        },
        deleteForm(){
            $.ajax({
                type: "DELETE",
                url: api_url+"form/deleteForm",
                headers: {
                    "token": localStorage.getItem("token")
                },
                data: {
                    formId: vm.formList[vm.currentShowIndex].formId,
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