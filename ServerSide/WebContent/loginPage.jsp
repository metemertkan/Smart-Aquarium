
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Smart Aquarium</title>
<script>
//localization strings for the user message find a better way to do it
var globals ={
	    en : {
	        usernameMissing:'Username must be filled out',
	        usernameRestriction:'Username must be be between 3 and 30 characters!',
			usernameRegex:'Username must be valid!',
	        passwordMissing:'Password must be filled out',
	        passwordRestriction:'Password must be be between 3 and 30 characters!',
			passwordRegex:'Password must be valid!'
	    }
	};
function validateForm() {
	//Client side validation 
	//get the username and password
    var username = document.forms["loginForm"]["username"].value;
    var userpass = document.forms["loginForm"]["userpass"].value;
    
    //regex strings for validation
    var ck_name=/^[A-Za-z0-9 ]{3,30}$/;
    
    
    //check username if empty
    if (username == null || username == "") {
        alert(globals['en'].usernameMissing);
        return false;
    }
    else if (username.length<3 || username.length>30){//check lenght of username
    	alert(globals['en'].usernameRestriction);
        return false;
    }
    //Check for regex
    if(!ck_name.test(username)){
        alert(globals['en'].usernameRegex);
        return false;
    }
    
    //check password if empty 
    if (userpass == null || userpass == "") {
        alert(globals['en'].passwordMissing);
        return false;
    }
    else if (userpass.length<3 || userpass.length>30){//check lenght of password
    	alert(globals['en'].passwordRestriction);
        return false;
    }
    
    //Check for regex
    if(!ck_name.test(userpass)){
        alert(globals['en'].passwordRegex);
        return false;
    }
    
}
</script>
</head>
<body>
<form name="loginForm" action="loginPage"  onsubmit="return validateForm()" method="post">  
Name:<input type="text" name="username"/><br/><br/>  
Password:<input type="password" name="userpass"/><br/><br/>  
<input type="submit" value="login"/>  
</form>
<div data-role="footer" data-theme="d"><h5>Kasetsart University Senior Project </h5></div> 

</body>
</html>