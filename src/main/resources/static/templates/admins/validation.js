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
    if(document.getElementByName("password").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function isEmailInputFullfill(){
    if(document.getElementByName("email").value !=""){
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
    if(document.getElementByName("degreeName").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function isMinCoolcoinsInputFullfill(){
    if(document.getElementByName("minCoolcoins").value !=""){
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
    if(document.getElementByName("className").value !=""){
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
//////////


//koniec
function isNameInputFullfill(){
    if(document.getElementById("nameInput").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function isEmailInputFullfill(){
    if(document.getElementById("emailInput").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function isTextInputFullfill(){
    if(document.getElementById("textInput").value !=""){
        return true;
    }
    else{
        return false;
    }
}

function enableSendButton(){
    var button =  document.getElementById("sendButton");
    if(isNameInputFullfill() && isEmailInputFullfill() && isTextInputFullfill()){
        button.disabled = false;
    }
    else if(isNameInputFullfill() || isEmailInputFullfill() || isTextInputFullfill()){
        button.disabled = true;
    }
}



function validateEmail(){
    var notOnlyWhiteSpaces = new RegExp("^\s*$");
    var correctEmail = new RegExp("^[\S]+@[\S]+[\.][\S]+");
    
    var emailInput = document.getElementById("emailInput").value; //nie działa

    if(!correctEmail.test(emailInput)){
        alert("Incorrect email address.");
    }
    else{
        if(notOnlyWhiteSpaces.test(emailInput)){                  // nie działa
            alert("Email: Put some signs here man.");
        }
        else{
            return true;
        }
    }
    return false;
}

function validateFields(){
    if(validateName() && validateEmail()){
        alert("Message has been sent out.");
        document.getElementById("nameInput").value = "";
        document.getElementById("emailInput").value = "";
        document.getElementById("textInput").value = "";
    }
}
