import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.filenet.wcm.api.*;

public class AddFaturaRic {

	public AddFaturaRic() throws Exception {
	}
 
	public AddFaturaRic(Arquivo arq, Session myCESession) {

    try {
 
	  System.out.println(" ********* In�cio AddFaturaRic *********** ");

      AddFaturaRic myInstance = new AddFaturaRic();

	  String docTitle = "";
	  if (RoboECMRic.instr(1,arq.getDESC_TIPO_DOCUMENTO(),"/") != 0){
		docTitle = RoboECMRic.mid(arq.getDESC_TIPO_DOCUMENTO(),1,RoboECMRic.instr(1,arq.getDESC_TIPO_DOCUMENTO(),"/")-1) +
				"-" +	 RoboECMRic.mid(arq.getDESC_TIPO_DOCUMENTO(),RoboECMRic.instr(1,arq.getDESC_TIPO_DOCUMENTO(),"/")+1,arq.getDESC_TIPO_DOCUMENTO().length()-RoboECMRic.instr(1,arq.getDESC_TIPO_DOCUMENTO(),"/"));	  
	  }
	  else{
		docTitle = arq.getDESC_TIPO_DOCUMENTO();
	  }
	  
	  System.out.println("docTitle " + docTitle);

	  java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
	  String folderApoliceName = arq.getCIA() + " " + arq.getSUCURSAL() + " " + 
							  arq.getPRODUTO() + " " + arq.getAPOLICE(); 
							   
//							 + " EM " + sdf.format(arq.getDATA_EMISSAO()) + 
//							  " VIG " + sdf.format(arq.getDATA_INICIO_VIGENCIA()) + " " + sdf.format(arq.getDATA_FIM_VIGENCIA()) +
//							   " - " + arq.getNOME_SEGURADO();

	  System.out.println("folderApoliceName " + folderApoliceName);
	  String fileName = "C:/WebDocRic/" + RoboECMRic.mid(arq.getNOME_ARQ(),1, RoboECMRic.instr(1,arq.getNOME_ARQ(),".TXT")-1) + "." + arq.getEXTENSAO_ARQUIVO();
	  System.out.println("fileName " + fileName);
	  String nomeDocFileNet = "/Emiss�o RIC/" + folderApoliceName + "/" + RoboECMRic.mid(arq.getNOME_ARQ(),1, RoboECMRic.instr(1,arq.getNOME_ARQ(),".TXT")-1) + "." + arq.getEXTENSAO_ARQUIVO();
	  System.out.println("nomeDocFileNet " + nomeDocFileNet);	 
	  String folderFaturaName = "Fatura " + arq.getFATURA(); 
	   
//	    					+    " EM " + sdf.format(arq.getDATA_EMISSAO()) + 
//	  							 " VIG " + sdf.format(arq.getDATA_INICIO_VIGENCIA()) + " " + sdf.format(arq.getDATA_FIM_VIGENCIA()) +
//								 " - " + arq.getNOME_SEGURADO();

	  System.out.println("folderFaturaName " + folderFaturaName);	
	    	  
	  ObjectStore objectStore = ObjectFactory.getObjectStore("FN_Desenv", myCESession);

//Testa se o Folder da Apolice existe.... Se n�o existir, cria e insere o arquivo de propriedades...
	  System.out.println("Testando se folder da apolice existe... " + folderApoliceName);
	  Folder aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,"/Emiss�o RIC/" + folderApoliceName);
	  if (aFolder.getClassId() == null){  //n�o existe
		aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,"/Emiss�o RIC");
		Folder parentFolder = aFolder.addSubFolder(folderApoliceName, null, null);
		System.out.println("Criou Folder da Apolice....");	
		//N�o tem association properties :
//		AddApoliceRic.AdicionaArqPropriedade(arq,objectStore,folderApoliceName);	
	  }
	  else{
		System.out.println("Folder da Apolice j� existe....");
	  }

//Testa se o Folder da Fatura existe....
	  System.out.println("Testando se folder de Fatura existe... " + folderFaturaName);
	  aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,"/Emiss�o RIC/" + folderApoliceName + "/" + folderFaturaName);
      if (aFolder.getClassId() == null){  //n�o existe
		aFolder = (Folder)objectStore.getObject(BaseObject.TYPE_FOLDER,"/Emiss�o RIC/" + folderApoliceName);
    	Folder parentFolder = aFolder.addSubFolder(folderFaturaName, null, null);		
      }
      else{
	    System.out.println("Folder Existente... Folder n�o criado....");
      }

	  com.filenet.wcm.api.Properties properties = ObjectFactory.getProperties();
      Property property =  ObjectFactory.getProperty(Property.DOCUMENT_TITLE);
      property.setValue(docTitle);
      properties.add(property);

//****     

	  property =  ObjectFactory.getProperty("CIA");
	  property.setValue(arq.getCIA());
	  properties.add(property);
	  property =  ObjectFactory.getProperty("SUCURSAL");
	  property.setValue(arq.getSUCURSAL());
	  properties.add(property);
	  property =  ObjectFactory.getProperty("CARTEIRA");
	  property.setValue(arq.getPRODUTO());
	  properties.add(property);	        
	  property =  ObjectFactory.getProperty("APOLICE");
	  property.setValue(arq.getAPOLICE());
	  properties.add(property);
	  property =  ObjectFactory.getProperty("NOME_SEGURADO");
	  property.setValue(arq.getNOME_SEGURADO());
	  properties.add(property);
	  property =  ObjectFactory.getProperty("CPF_CNPJ");
	  property.setValue(arq.getCPF_CNPJ());
	  properties.add(property);
		  
	  property =  ObjectFactory.getProperty("DATA_EMISSAO");
	  property.setValue(arq.getDATA_EMISSAO());
	  properties.add(property);
	
	  property =  ObjectFactory.getProperty("DAT_INIC_VIGENCIA");
	  property.setValue(arq.getDATA_INICIO_VIGENCIA());
	  properties.add(property);	  
	
	  property =  ObjectFactory.getProperty("DAT_FIM_VIGENCIA");
	  property.setValue(arq.getDATA_FIM_VIGENCIA());
	  properties.add(property);
		  
	  sdf = new SimpleDateFormat("dd/MM/yyyy");
	  String data = sdf.format(new Date());
	  sdf.setLenient(false);	  
	  java.util.Date date = sdf.parse(data);
	  property =  ObjectFactory.getProperty("DAT_INCLUSAO_ECM");
	  property.setValue(date);
	  properties.add(property);
	
	  property =  ObjectFactory.getProperty("NOME_FUNCIONARIO");
	  property.setValue(arq.getNOME_FUNCIONARIO());
	  properties.add(property);
		  
	  property =  ObjectFactory.getProperty("DESCRICAO");
	  property.setValue(arq.getDESC_TPO_APO_END());
	  properties.add(property);
		  
	  property =  ObjectFactory.getProperty("DESC_TIPO_DOCUMENTO");
	  property.setValue(arq.getDESC_TIPO_DOCUMENTO());
	  properties.add(property);
		  
	  property =  ObjectFactory.getProperty("SIGLA_SISTEMA");
	  property.setValue(arq.getSIGLA_SISTEMA());
	  properties.add(property);
	       
	  property =  ObjectFactory.getProperty("FATURA");
	  property.setValue(arq.getFATURA());
	  properties.add(property);
	  
//	  String documentNameApo = "/Emiss�o RIC/" + folderApoliceName + "/" + "Propriedades";
//	  System.out.println("documentNameApo " + documentNameApo);	
	  
	  
	  //N�o tem association properties :	  
// Document documentApo = (Document) objectStore.getObject(BaseObject.TYPE_DOCUMENT, documentNameApo);
// property =  ObjectFactory.getProperty("DOCS_APOLICE");
// property.setValue(documentApo);
// properties.add(property);	  	  
	  	  	  	  
      try {
      //Document document = (Document) objectStore.createObject(ClassDescription.DOCUMENT, properties, null);
	    Document document = (Document) objectStore.createObject("FATURA_RIC", properties, null);
        Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, "/Emiss�o RIC/" + folderApoliceName + "/" + folderFaturaName);
        document.file(folder, false);
       
        TransportInputStream content = new TransportInputStream(new FileInputStream(fileName));
        document.setContent(content, false, false);
        document.checkin(false);
		System.out.println("Documento Fatura Adicionado com sucesso. Doc -> "+nomeDocFileNet);
      }
	  catch (com.filenet.wcm.api.UniquenessConstraintException e) {
		System.out.println("Documento j� existente... filename ----> " + fileName);
		System.out.println("nomeDocFileNet ------------------------> " + nomeDocFileNet);
		
		Document document = (Document) objectStore.createObject("FATURA_RIC", properties, null);
		Folder folder = (Folder) objectStore.getObject (BaseObject.TYPE_FOLDER, "/Emiss�o RIC/" + folderApoliceName + "/" + folderFaturaName);
		document.file(folder, true);
       
		TransportInputStream content = new TransportInputStream(new FileInputStream(fileName));
		document.setContent(content, false, false);
		document.checkin(false);
		System.out.println("Documento Fatura Adicionado com mesmo nome com sucesso. Doc -> "+nomeDocFileNet);
				
	  }
      
	  System.out.println(" *********  Fim AddFaturaRic  *********** ");
	  System.out.println("  ");	  
	        
     }catch (Exception e) {
      e.printStackTrace();
     }
  
   }
}
