/* 用于 本地缓存为空的时候(  getItem( 菜单) 为空  ) 插入一些初始化的菜单数据 */
/*ID 序号  menu_name菜单名字  fid 父菜单的ID url 跳转路径 */
var menuArr = [ 
    /* 1~4一级菜单 */
     {"id":1, "menu_name":"用户信息管理","fid":0,"url":"" } ,
     {"id":2, "menu_name":"英雄信息管理","fid":0,"url":"" } ,
     {"id":3, "menu_name":"订单信息管理","fid":0,"url":"" } ,
     {"id":4, "menu_name":"数据统计管理","fid":0,"url":"" } ,
  /* 二级菜单 */
     {"id":5, "menu_name":"管理员账号","fid":1,"url":"./管理员账号管理.html" } ,
     {"id":6, "menu_name":"用户账号","fid":1,"url":"./玩家账号.html" } ,
     {"id":7, "menu_name":"英雄列表","fid":2,"url":"./英雄管理.html" } ,
     {"id":8, "menu_name":"已支付订单","fid":3,"url":"./已支付订单.html" } ,
     {"id":9, "menu_name":"未支付订单","fid":3,"url":"./未支付订单.html" } ,
     {"id":10, "menu_name":"数据统计","fid":4,"url":"./数据统计.html" } 
] ; 





    // JSON.stringify(  userArr ) ;  转化成字符串 因为localStorage只能存储字符串
    localStorage.setItem("menu",JSON.stringify(  menuArr ) );

