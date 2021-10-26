 


var menuArr = JSON.parse( localStorage.getItem("menu") ) ; 


//JS 方式 
var domStr =`` ; 
for (  var i = 0 ;i<=menuArr.length-1 ; i++ ){
    if (  menuArr[i].fid == 0   ){
        domStr+=`<h3 onclick="toggle(event)">${   menuArr[i].menu_name }</h3>`;
        domStr+=`<ul>`
        for(var j = 0 ; j<= menuArr.length-1 ;j++  ){
            if (menuArr[j].fid ==  menuArr[i].id ){
                domStr+=`<li onclick="toPage('${menuArr[j].url}')">${menuArr[j].menu_name}</li>`
            } ; 
        }
        domStr+=`</ul>`
    }
}

document.getElementById("menu").innerHTML = domStr;


function toggle(e){
    // $(e.target ).next() 下一个元素 
     $(e.target ).next().slideToggle(100)   ;
}

function toPage(url){
    $("#info").attr('src', url ) ; 
}