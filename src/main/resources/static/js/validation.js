// login validation
function isLoginInputFullfill(){
    if(document.getElementById("loginInput").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function isPasswordInputFullfill(){
    if(document.getElementById("passwordInput").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function validateLogin(){
    var button =  document.getElementById("loginButton");
    if(isLoginInputFullfill() && isPasswordInputFullfill() ){
        button.disabled = false;
    }
}
//////////


function isLastNameInputProfileFullfill(){
    if(document.getElementByName("lastName").value !=""){
        return true;
    }
    else{
        return false;
    }
}
// admin profile validation
function isPasswordProfileInputFullfill(){
    if(document.getElementById("passwordProfile").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function isEmailInputFullfill(){
    if(document.getElementById("emailProfile").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function validateAdminProfile(){
    var button =  document.getElementById("changeAdminProfileButton");
    if(isEmailInputFullfill() && isPasswordProfileInputFullfill() ){
        button.disabled = false;
    }
}
//////////////////


// degree inputs validation
function isDegreeNameInputFullfill(){
    if(document.getElementById("nameDegrees").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function isMinCoolcoinsInputFullfill(){
    if(document.getElementById("minCoolcoinsDegrees").value !=""){
        return true;
    }
    else{
        return false;
    }
}
function validateDegree(){
    var button =  document.getElementById("changeDegreeButton");
    if(isDegreeNameInputFullfill() && isMinCoolcoinsInputFullfill() ){
        button.disabled = false;
    }
}
////////////

//class inputs validation
function isClassNameInputFullfill(){
    if(document.getElementById("nameClasses").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function validateClass(){
    var button =  document.getElementById("changeClassButton");
    if(isClassNameInputFullfill()){
        button.disabled = false;
    }
}

//koniec
function validateEmail(){
    var correctEmail = new RegExp(".@.");
    var emailInput = document.getElementById("emailProfile").value;

    var button =  document.getElementById("changeAdminProfileButton");


    if(!correctEmail.test(emailInput)){
        button.disabled="true";
    }
    else{
        button.disabled="false";
    }
}

function validateFields(){
    if(validateName() && validateEmail()){
        alert("Message has been sent out.");
        document.getElementById("nameInput").value = "";
        document.getElementById("emailInput").value = "";
        document.getElementById("textInput").value = "";
    }
}
