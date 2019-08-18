var $JQuery = jQuery.noConflict();
$JQuery("#userForm").submit(function(event) { 
	alert("OKKK");
	$JQuery("#loading").show();
			var url = "/openmrs/ws/rest/v1/service-management/saves";			
			var token = $JQuery("meta[name='_csrf']").attr("content");
			var header = $JQuery("meta[name='_csrf_header']").attr("content");			
			
			
			var clinicValue = $JQuery('input[name=psiClinicManagement]').val();
			var firstName = $JQuery('input[name=firstName]').val();
			var lastName = $JQuery('input[name=lastName]').val();
			var email = $JQuery('input[name=email]').val();
			var mobile = $JQuery('input[name=mobile]').val();
			var userName = $JQuery('input[name=userName]').val();
			var password = $JQuery('input[name=password]').val();
			
			var gender = getGender();
			var roles = getRoles();
			alert(roles);
			var formData;			
				formData = {
			            'firstName': firstName,			           
			            'lastName': lastName,
			            'email': email,
			            'mobile': mobile,
			            'sid':  $JQuery('input[name=sid]').val(),
			            'userName': userName,
			            'psiClinicManagement': clinicValue,
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
					$JQuery("#usernameUniqueErrorMessage").html(data);
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
 } 

function getRoles(){
	/* declare an checkbox array */
	var chkArray = [];
	
	/* look for all checkboes that have a class 'chk' attached to it and check if it was checked */
	$(".roles:checked").each(function() {
		chkArray.push($(this).val());
	});
	
	/* we join the array separated by the comma */
	var selected;
	selected = chkArray.join(',') ;		
	alert(selected);
	return selected;
}