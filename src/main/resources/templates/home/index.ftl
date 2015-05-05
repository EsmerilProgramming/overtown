<html>
<head><title>Undertow Chat</title>
</head>
<body>
<div class="page">
    <div class="center" >
        <form onsubmit="return false;" class="chatform" action="">
            <label for="msg">Message</label>
            <input type="text" name="message" id="msg"  onkeypress="if(event.keyCode==13) { send(this.form.message.value); this.value='' } " />
        </form>
    </div>
</div>
</body>
</html>