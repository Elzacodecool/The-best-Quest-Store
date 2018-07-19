function validateLogin() {
                var userName = document.getElementById("loginInput").value;
                var pass = document.getElementById("passwordInput").value;

                var codecoolerName = "c";
                var codecoolerPass = "c";

                var mentorName = "m";
                var mentorPass = "m";
                
                var adminName = "a";
                var adminPass = "a";

                if ((userName == codecoolerName) && (pass == codecoolerPass)) {
                    window.location = "codecooler_profile.html";
                    return false;
                }
                if ((userName == adminName) && (pass == adminPass)) {
                    window.location = "admin_profile.html";
                    return false;
                }
                if ((userName == mentorName) && (pass == mentorPass)) {
                    window.location = "mentor_profile.html";
                    return false;
                }
                else {
                    alert ("Login was unsuccessful!");
                }
            }
