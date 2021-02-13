(()=>{
 document.querySelector('.send').addEventListener('click',()=>{
     let text = fms.value;
     let transDiv = document.querySelector('.trans');
     let url ="/movie/translation";
     console.log("매개변수?"+text);
     if(text){
         console.log("fetch돈다 ");
         fetch(url,{
             method:"POST",
             headers:{
                 'Content-Type': 'application/x-www-form-urlencoded'
             },
             body : "text="+text
         }).then(response=>{
             if(response.ok){
                 return response.text();
             }
             //throw new AsyncPageError(response.text());
         })
             .then((msg)=>{
                 if(msg == 'fail'){
                     transDiv.innerHTML = '번역할 수 없습니다.';
                 } else {
                     transDiv.innerHTML = msg;
                 }
             }).catch(error=>{
             error.alertMessage();
         })
     } else {
         alert("입력하지 않았습니다.");
     }
 })

})();


