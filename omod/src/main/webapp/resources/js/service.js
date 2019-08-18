var $JQuery = jQuery.noConflict();
$JQuery("#serviceForm").submit(function(event) { 
	$JQuery("#loading").show();
			var url = "/openmrs/ws/rest/v1/service-management/save";			
			var token = $JQuery("meta[name='_csrf']").attr("content");
			var header = $JQuery("meta[name='_csrf_header']").attr("content");			
			
			var e = document.getElementById("category");
			var category = e.options[e.selectedIndex].value;
			var e1 = document.getElementById("provider");
			var provider = e1.options[e1.selectedIndex].value;
			//var clinic = document.getElementById("psiClinicManagement");
			var clinicValue = $JQuery('input[name=psiClinicManagement]').val();
			var yearTo = $JQuery('input[name=yearTo]').val();
			var monthTo = $JQuery('input[name=monthTo]').val();
			var daysTo = $JQuery('input[name=daysTo]').val();
			var yearFrom = $JQuery('input[name=yearFrom]').val();
			var monthFrom = $JQuery('input[name=monthFrom]').val();
			var daysFrom = $JQuery('input[name=daysFrom]').val();
			
			var gender = document.getElementById("gender");
			var genderValue = gender.options[gender.selectedIndex].value;
			var formData;			
				formData = {
			            'name': $JQuery('input[name=name]').val(),			           
			            'code': $JQuery('input[name=code]').val(),
			            'category': category,
			            'provider': provider,
			            'sid':  $JQuery('input[name=sid]').val(),
			            'unitCost': $JQuery('input[name=unitCost]').val(),
			            'psiClinicManagement': clinicValue,
			            'yearTo': yearTo,
			            'monthTo': monthTo,
			            'daysTo': daysTo,
			            'yearFrom': yearFrom,
			            'monthFrom': monthFrom,
			            'gender': genderValue,
			            'daysFrom': daysFrom
			           
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