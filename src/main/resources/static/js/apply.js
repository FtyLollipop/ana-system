var vm = new Vue({
    el:"#app",
    data:{
        role:0,
        stateFilter:"all",
        currentPage:0,
        totalPage:0,
        formList:[
            {
                formId:"1",
                userId:"1",
                realName:"滑稽",
                title:"申请做某事",
                content:"申请去做。。。申请去做。。。申请去做。。。申请去做。。。申请去做。。。申请去做。。。申请去做。。。",
                createTime:"2021-10-26 20:30:26",
                state:"0",
                approveTime:"2021-10-26 20:30:26",
            }
        ]
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
    },
    created:function(){
        
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
        getForms(){
            
        }
    }
})