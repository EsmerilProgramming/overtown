<html>
<head>
    <script src="/static/jquery-1.10.2.min.js" ></script>
</head>
<body>
 Parsed template MY FRIEND
 <br/>
 Hello: ${name}
 
 
 <form>
    <button id="test">With Params</button>
    <button id="testWithoutParams">Without Params</button>
 </form>
    
<script>
    $("#test").click( function(){
        $.post("/app/management/teste4" , { x : 10 } , function(d){
            alert(d);
        });
        return false;    
    });
    
     $("#testWithoutParams").click( function(){
        $.post("/app/management/teste4" , function(d){
            alert(d);
        });
        return false;    
    });
</script>
</body>
</html>