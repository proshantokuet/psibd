var $JQuery = jQuery.noConflict();
$JQuery("#userForm").submit(function(event) { 
			
			$JQuery("#loading").show();
			var url = "/openmrs/ws/rest/v1/clinic-user/save";			
			var token = $JQuery("meta[name='_csrf']").attr("content");
			var header = $JQuery("meta[name='_csrf_header']").attr("content");		
			
			var clinicValue = $JQuery('input[name=clinicId]').val();
			var firstName = $JQuery('input[name=firstName]').val();
			var lastName = $JQuery('input[name=lastName]').val();
			var email = $JQuery('input[name=email]').val();
			var mobile = $JQuery('input[name=mobile]').val();
			var userName = $JQuery('input[name=userName]').val();
			var password = $JQuery('input[name=password]').val();			
			var gender = getGender();
			alert(gender);
			var roles = getRoles();
			alert(roles);
			var formData;			
				formData = {
			            'firstName': firstName,			           
			            'lastName': lastName,
			            'email': email,
			            'mobile': mobile,
			            'cuid':  $JQuery('input[name=cuid]').val(),
			            'userName': userName,
			            'clinicId': clinicValue,
			            'password': password,
			            'gender': gender,
			            'roles': roles
			           
			       };			
			
			event.preventDefault();			
			$JQuery.ajax({
				contentType : "application/json",
				type: "POST",
		        url: url,
		        data: JSON.stringify(formData), 
		        dataType : 'json',
		        
				timeout : 100000,
				beforeSend: function(xhr) {				    
					 xhr.setRequestHeader(header, token);
				},
				success : function(data) {
					//$JQuery("#usernameUniqueErrorMessage").html(data);
					$JQuery("#loading").hide();
				   if(data == ""){					   
					   window.location.replace("/openmrs/module/PSI/PSIClinicServiceList.form?id="+clinicValue);
					   
				   }
				   
				},
				error : function(e) {
				   
				},
				done : function(e) {				    
				    console.log("DONE");				    
				}
			}); 
		});


function getGender() { 
    var gender = document.getElementsByName('gender');
	for (var i = 0, length = gender.length; i < length; i++) {
	    if (gender[i].checked) {		
	    	return gender[i].value;
	    }
	}
	return -1;
 } 

function getRoles(){
	/* declare an checkbox array */
	var chkArray = [];
	
	/* look for all checkboes that have a class 'chk' attached to it and check if it was checked */
	$JQuery(".roles:checked").each(function() {
		chkArray.push($JQuery(this).val());
	});
	
	/* we join the array separated by the comma */
	var selected;
	selected = chkArray.join(',') ;		
	alert(selected);
	return selected;
}

function checkPassword(inputtxt) { 
	var re = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])\w{8,}$/;
    return re.test(inputtxt);
}
function Validate() {
	 
	if(getGender() == -1){
		$JQuery("#mesage").html("Please select gender");
		$JQuery("#mesage").show();
		
		$JQuery('html, body').animate({ scrollTop: 0 }, 'slow');
		document.getElementById("M").focus();
		return false;
	}
	
	$JQuery("#mesage").hide();
    $JQuery("#mesage").html("");
	
	var password = document.getElementById("password").value;
    var confirmPassword = document.getElementById("reTypePassword").value;
    if (password != confirmPassword) {
    	$JQuery("#mesage").html("Your password is not similar with confirm password. Please enter same password in both");
    	$JQuery("#mesage").show();
    	$JQuery('html, body').animate({ scrollTop: 0 }, 'slow');
    	document.getElementById("reTypePassword").focus();
   	 return false;
    }
    $JQuery("#mesage").hide();
    $JQuery("#mesage").html("");
    alert(checkPassword(password));
    if(!checkPassword(password)){
    	$JQuery("#mesage").html("Please choose a password that is at least 8 characters long that contains both upper- and lower-case letters and  at least one number");
    	$JQuery("#mesage").show();
    	$JQuery('html, body').animate({ scrollTop: 0 }, 'slow');
    	document.getElementById("password").focus();
    	return false;
    }
    
    $JQuery("#mesage").hide();
    $JQuery("#mesage").html("");
    
	var selected;
	selected = getRoles() ;		
	if(selected.length > 0){			
	}else{			
		$JQuery("#mesage").html("Please select at least one role");
		$JQuery("#mesage").show();
		$JQuery('html, body').animate({ scrollTop: 0 }, 'slow');
		
		return false;
	}
	$JQuery("#mesage").html("");
	$JQuery("#mesage").hide();
    return true;
}
