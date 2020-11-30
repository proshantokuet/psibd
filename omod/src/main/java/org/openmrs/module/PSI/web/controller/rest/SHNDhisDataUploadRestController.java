package org.openmrs.module.PSI.web.controller.rest;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.openmrs.module.PSI.converter.DHISDataConverter;
import org.openmrs.module.PSI.dhis.service.PSIAPIServiceFactory;
import org.openmrs.module.PSI.dto.SHNHistoricalDataDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

@RequestMapping("/rest/v1/dhis-upload")
@RestController
public class SHNDhisDataUploadRestController {
	
	@Autowired
	private PSIAPIServiceFactory psiapiServiceFactory;
	protected final Log log = LogFactory.getLog(getClass());

	
	@SuppressWarnings("resource")
	@RequestMapping(value = "/historical-data", method = RequestMethod.POST)
	public ResponseEntity<String> uploadProduct(@RequestParam MultipartFile file, HttpServletRequest request,
	                                                  ModelMap model)
	    throws Exception {
		
		JSONObject historicalJsonData = null;
		String msg = "";
		String failedMessage = "";
		if (file.isEmpty()) {
			msg = "failed to upload file because its empty";
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
			
		}
		
		String rootPath = request.getSession().getServletContext().getRealPath("/");
		File dir = new File(rootPath + File.separator + "uploadedfile");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		File csvFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
		
		try {
			try (InputStream is = file.getInputStream();
			        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(csvFile))) {
				int i;
				
				while ((i = is.read()) != -1) {
					stream.write(i);
				}
				stream.flush();
			}
		}
		catch (IOException e) {
			msg = "failed to process file because : " + e.getMessage();
			return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		}
		
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		int index = 0;
		int notUploded = 0;
		try {
			br = new BufferedReader(new FileReader(csvFile));
			
			while ((line = br.readLine()) != null) {
				String[] service = line.split(cvsSplitBy);
				if (index != 0) {
					SHNHistoricalDataDTO shnHistoricalDataUpload = new SHNHistoricalDataDTO();
					String comp_id = "";
					if (!StringUtils.isBlank(service[0])) {
						comp_id = service[0];
					}
					shnHistoricalDataUpload.setHdComp_id(comp_id);
					
					String Customer_id = "";
					if (!StringUtils.isBlank(service[1])) {
						Customer_id = service[1];
					}
					shnHistoricalDataUpload.setHdCustomer_id(Customer_id);
					
					String Ptype = "";
					if (!StringUtils.isBlank(service[2])) {
						Ptype = service[2];
					}
					shnHistoricalDataUpload.setHdPtype(Ptype);
					
					String sex = "";
					if (!StringUtils.isBlank(service[3])) {
						sex = service[3];
					}
					shnHistoricalDataUpload.setHdsex(sex);

					String Card = "";
					if (!StringUtils.isBlank(service[4])) {
						Card = service[4];
					}
					shnHistoricalDataUpload.setHdCard(Card);

					String WtQ = "";
					if (!StringUtils.isBlank(service[5])) {
						WtQ = service[5];
					}
					shnHistoricalDataUpload.setHdWtq(WtQ);
					
					String idno = "";
					if (!StringUtils.isBlank(service[6])) {
						idno = service[6];
					}
					shnHistoricalDataUpload.setHdidno(idno);
					
					String compId = "";
					if (!StringUtils.isBlank(service[7])) {
						compId = service[7];
					}
					shnHistoricalDataUpload.setHdCompId(compId);
					
					String Customer_name = "";
					if (!StringUtils.isBlank(service[8])) {
						Customer_name = service[8];
					}
					shnHistoricalDataUpload.setHdCustomer_name(Customer_name);
					
					String Day = "";
					if (!StringUtils.isBlank(service[9])) {
						Day = service[9];
					}
					shnHistoricalDataUpload.setHdDay(Day);
					
					String Month = "";
					if (!StringUtils.isBlank(service[10])) {
						Month = service[10];
					}
					shnHistoricalDataUpload.setHdMonth(Month);
					
					String Year = "";
					if (!StringUtils.isBlank(service[11])) {
						Year = service[11];
					}
					shnHistoricalDataUpload.setHdYear(Year);
					
					String Bkkno = "";
					if (!StringUtils.isBlank(service[12])) {
						Bkkno = service[12];
					}
					shnHistoricalDataUpload.setHdkkno(Bkkno);
					
					String Bkno = "";
					if (!StringUtils.isBlank(service[13])) {
						Bkno = service[13];
					}
					shnHistoricalDataUpload.setHdBkno(Bkno);
					
					String voucherno = "";
					if (!StringUtils.isBlank(service[14])) {
						voucherno = service[14];
					}
					shnHistoricalDataUpload.setHdvoucherno(voucherno);
					
					String NGOId = "";
					if (!StringUtils.isBlank(service[15])) {
						NGOId = service[15];
					}
					shnHistoricalDataUpload.setHdNgoid(NGOId);
					
					String ClinicId = "";
					if (!StringUtils.isBlank(service[16])) {
						ClinicId = service[16];
					}
					shnHistoricalDataUpload.setHdClinicId(ClinicId);
					
					String SatelliteId = "";
					if (!StringUtils.isBlank(service[17])) {
						SatelliteId = service[17];
					}
					shnHistoricalDataUpload.setHdSatelliteId(SatelliteId);
					
					String teamId = "";
					if (!StringUtils.isBlank(service[18])) {
						teamId = service[18];
					}
					shnHistoricalDataUpload.setHdteamId(teamId);
					
					String CSPID = "";
					if (!StringUtils.isBlank(service[19])) {
						CSPID = service[19];
					}
					shnHistoricalDataUpload.setHdCspId(CSPID);
					
					String MoblieNo = "";
					if (!StringUtils.isBlank(service[20])) {
						MoblieNo = service[20];
					}
					shnHistoricalDataUpload.setHdMoblieno(MoblieNo);
					
					String Age_day = "";
					if (!StringUtils.isBlank(service[21])) {
						Age_day = service[21];
					}
					shnHistoricalDataUpload.setHdAge_day(Age_day);
					
					String Age_month = "";
					if (!StringUtils.isBlank(service[22])) {
						Age_month = service[22];
					}
					shnHistoricalDataUpload.setHdAge_month(Age_month);
					
					String Age_year = "";
					if (!StringUtils.isBlank(service[23])) {
						Age_year = service[23];
					}
					shnHistoricalDataUpload.setHdAge_year(Age_year);
					
					String session = "";
					if (!StringUtils.isBlank(service[24])) {
						session = service[24];
					}
					shnHistoricalDataUpload.setHdsession(session);
					
					String Adolescent = "";
					if (!StringUtils.isBlank(service[25])) {
						Adolescent = service[25];
					}
					shnHistoricalDataUpload.setHdAdolescent(Adolescent);
					
					String hdService = "";
					if (!StringUtils.isBlank(service[26])) {
						hdService = service[26];
					}
					shnHistoricalDataUpload.setHdServiceCode(hdService);
					
					String QTY = "";
					if (!StringUtils.isBlank(service[27])) {
						QTY = service[27];
					}
					shnHistoricalDataUpload.setHdQty(QTY);
					
					String Taka = "";
					if (!StringUtils.isBlank(service[28])) {
						Taka = service[28];
					}
					shnHistoricalDataUpload.setHdTaka(Taka);
					
					String DTK = "";
					if (!StringUtils.isBlank(service[29])) {
						DTK = service[29];
					}
					shnHistoricalDataUpload.setHdDtk(DTK);
					
					historicalJsonData = DHISDataConverter.toConvertDhisHistoricalData(shnHistoricalDataUpload);
					
					
					
				}
				index++;
			}
			//msg = "Total successfully product uploaded: " + (index - 1);
			msg = historicalJsonData.toString();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			failedMessage = "failed to process file because : " + e;
			return new ResponseEntity<>(new Gson().toJson(msg + ", and  got error at column : " + (index + 1) + " due to "
			        + failedMessage), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(new Gson().toJson(msg), HttpStatus.OK);
		
	}

}
