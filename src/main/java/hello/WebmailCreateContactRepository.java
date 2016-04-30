package hello;

import javax.annotation.PostConstruct;
import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
//import javax.jcr.Session;
import javax.jcr.SimpleCredentials;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import javax.mail.*;
import javax.mail.Flags.Flag;
import javax.mail.internet.*;
import javax.naming.*;
import javax.naming.directory.*;

import java.io.*;

import io.spring.guides.gs_producing_web_service.Country;
import io.spring.guides.gs_producing_web_service.Currency;

import org.apache.commons.io.IOUtils;
import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.core.TransientRepository;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.edms.service.FolderService;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.imap.IMAPStore;
import com.webmail.createcontact.DisplayContact;
import com.webmail.ldapattribute.ArrayOfVCFLdapDir;
import com.webmail.ldapattribute.VCFLdapDirAtt;
import com.webmail.ldapattribute.VCFLdapDirListReturn;
import com.webmail.ldapdirectory.MyDirectory;
import com.webmail.quota.Webmailquota;

import edms.core.Config;
import ezvcard.*;
import ezvcard.io.text.VCardReader;
import ezvcard.parameter.*;
import ezvcard.property.*;
import ezvcard.property.Address;

@Component
public class WebmailCreateContactRepository {


/*	@Autowired
	FolderService folderService;*/

	
	
	public String getVCFIOStreame(String vcffilename,String note,String fullname,String company,String job,String email,String web_page,String phone_work,String phone_home,String phone_fax,String phone_mob,String addr_work,String addr_home,String pre,String fnm,String mnm,String lnm,String suf, List<Byte> photobase64)
	{
		Byte [] Bt = photobase64.toArray(new Byte[photobase64.size()]);
		byte [] bt=new byte[Bt.length];
		int j=0;
		// Unboxing byte values. (Byte[] to byte[])
			for(Byte b: Bt)
		    bt[j++] = b.byteValue();
			String iostream="";
			VCard vcard = new VCard();
		   // String nm="Abhay";
		    vcard.setKind(Kind.individual());

		    //vcard.setGender(Gender.male());

		    vcard.addLanguage("en-US");
		    vcard.addNote(note);
		    StructuredName n = new StructuredName();
		   if(pre!=null && !(pre.equalsIgnoreCase("")))
		   	{
			   n.addPrefix(pre);
		   	}
		   if(fnm!=null && !(fnm.equalsIgnoreCase("")))
		   {
			   n.setGiven(fnm+" "+mnm);
		   }
		   if(lnm!=null && !(lnm.equalsIgnoreCase("")))
		   {
			   n.setFamily(lnm);
		   }
		   if(suf!=null && !(suf.equalsIgnoreCase("")))
		   {
			   n.addSuffix(suf);
		   }
		    
		    vcard.setStructuredName(n);
		   // vcard.setSortString("Abhay");
		    vcard.setFormattedName(fullname);
		    /*if(mnm!=null && !(mnm.equalsIgnoreCase("")))
			   {
		    	vcard.setFormattedName(fnm+" "+mnm+" "+lnm);
			   }
		    else
		    	{
		    	vcard.setFormattedName(fnm+" "+lnm);
		    	}
*/
		    //vcard.setNickname("Abhay", "Aps");
		    if(job!=null && !(job.equalsIgnoreCase("")))
			   {
		    	vcard.addTitle(job);
			   }
		    if(company!=null && !(company.equalsIgnoreCase("")))
			   {
		    	vcard.setOrganization(company, "Department");
			   }
		    if(addr_work!=null && !(addr_work.equalsIgnoreCase("")))
			   {
		    Address adr = new Address();
		    adr.setStreetAddress(addr_work);
		  //  adr.setLocality("New York");
		   // adr.setRegion("NY");
		 //   adr.setPostalCode("12345");
		  //  adr.setCountry("USA");
		    adr.setLabel(addr_work);
		    adr.addType(AddressType.WORK);
		    vcard.addAddress(adr);
			   }
		    if(addr_home!=null && !(addr_home.equalsIgnoreCase("")))
			   {
		    	 Address adr = new Address();	
		   // adr = new Address();
		    adr.setStreetAddress(addr_home);
		    //adr.setLocality("Albany");
		   // adr.setRegion("NY");
		   // adr.setPostalCode("54321");
		    //adr.setCountry("USA");
		    adr.setLabel(addr_home);
		    adr.addType(AddressType.HOME);
		    vcard.addAddress(adr);
			   }
		    
		    if(phone_work!=null && !(phone_work.equalsIgnoreCase("")))
			   {
		    	vcard.addTelephoneNumber(phone_work, TelephoneType.WORK);
			   }

		    if(phone_home!=null && !(phone_home.equalsIgnoreCase("")))
			   {
		    	vcard.addTelephoneNumber(phone_home, TelephoneType.HOME);
			   }
		    
		    if(phone_mob!=null && !(phone_mob.equalsIgnoreCase("")))
			   {
		    	vcard.addTelephoneNumber(phone_mob, TelephoneType.CELL);
			   }
		    
		    if(phone_fax!=null && !(phone_fax.equalsIgnoreCase("")))
			   {
		    	vcard.addTelephoneNumber(phone_fax, TelephoneType.FAX);
			   }
		    
		    if(email!=null && !(email.equalsIgnoreCase("")))
			   {
		    	vcard.addEmail(email, EmailType.WORK);
			   }
			   if(web_page!=null && !(web_page.equalsIgnoreCase("")))
			   {
				   vcard.addUrl(web_page);
			   }
			   
			 Photo photo=null;
			 //  String base64="";
			  byte data[] =null; // photobase64.getBytes();
				//photo = new Photo(photobase64, ImageType.JPEG);
			 photo = new Photo(bt, ImageType.JPEG);
				vcard.addPhoto(photo);
			  
			   
			   File fil=null;
			   // System.out.println("Writing " + file.getName() + "...");
			   InputStream fs=null;
			    try
			    {
			    int idx = vcffilename.lastIndexOf('.');
                String fileExtension = idx > 0 ? vcffilename.substring(idx) : ".tmp";
                fil = File.createTempFile( vcffilename.substring(0,idx), fileExtension);
                //filecheck.transferTo(fil);
                Ezvcard.write(vcard).version(VCardVersion.V4_0).go( fil );
                System.out.println("file path="+fil.getPath());
                fs=new FileInputStream(fil);
                iostream= IOUtils.toString(fs);
			    }
			    catch(IOException e)
			    {
			    	e.printStackTrace();
			    }
			    finally
			    {
			    	fil.delete();
			    }
		   /* vcard.setCategories("widgetphile", "biker", "vCard expert");

		    vcard.setGeo(37.6, -95.67);
*/
		  /*  java.util.TimeZone tz = java.util.TimeZone.getTimeZone("America/New_York");
		    vcard.setTimezone(new Timezone(tz));*/

		   // File file = new File("portrait.jpg");
		   /* Photo photo = new Photo("https://mail.silvereye.in/photo/piyush@silvereye.in.jpg", ImageType.JPEG);
		    vcard.addPhoto(photo);*/
		    
		   /* file = new File("pronunciation.ogg");
		    Sound sound = new Sound(file, SoundType.OGG);
		    vcard.addSound(sound);
		    vcard.setUid(Uid.random());
		    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss.SSS").format(new Date());
		    guid=nm+"_"+timeStamp;
		    vcard.setRevision(Revision.now());*/
		
		
		return iostream;	
			
	
	}
	
	
	
	public DisplayContact dispContactDetails(String vcfiostrm, String flname)
	{
		DisplayContact dispres=new DisplayContact();
		dispres.setWebamilVCFFileName(flname);
		try
		{
		InputStream is= IOUtils.toInputStream(vcfiostrm);
		
		 VCardReader vcardReader = new VCardReader(is);
		 VCard vcard = Ezvcard.parse(is).first();
		 FormattedName fn=vcard.getFormattedName();
		 dispres.setWebamilFullName(fn.getValue());
		 
		 List<Title> list_tit =vcard.getTitles();
		 for(Title tit: list_tit)
		 {
			 dispres.setWebamilJob(tit.getValue());
			 break;
		 }
		 List <Photo> phlst=vcard.getPhotos();
		 String ph1="";
		 for(Photo ph: phlst)
		 {
			// ph1=ph.getContentType().toString();
			 byte[] bt=ph.getData();
			 ph1= new sun.misc.BASE64Encoder().encode(bt);
			 dispres.setWebamilPhoto(ph1); 
			 Byte []bbt=new Byte[bt.length];
			
			 int i=0;    
			 // Associating Byte array values with bytes. (byte[] to Byte[])
			 for(byte b: bt)
			    bbt[i++] = b;  // Autoboxing.
			 List<Byte> listbyte =Arrays.asList(bbt);
			 dispres.getWebamilPhotoByte().addAll(listbyte);
		 }
		// System.out.println("!!!!!!!!!!!!!!!!!!!Address:"+ad.getStreetAddress()+" type:"+ad.getTypes());
		Organization org= vcard.getOrganization();
	if(org!=null)
	{
		List<String> orgvlst= org.getValues();
		if(orgvlst.size()>0)
		{
		dispres.setWebamilCompany( orgvlst.get(0));
		//System.out.println("^^^^^^^^^^^org="+orgvlst.get(0));
		}
		else
		{
			dispres.setWebamilCompany( "");
		}
	}
		List<Note> notelst= vcard.getNotes();
		for(Note nt: notelst)
		{
			dispres.setWebamilNote(nt.getValue());
			break;
		}
		 
		 
		 List<Email> elst=	vcard.getEmails();
			
			 for(Email em: elst)
			 {
				 dispres.setWebamilEmail(em.getValue());
				// System.out.println("**************email type="+em.getTypes()+": email="+em.getValue());
				 break;
			 }
			 
			 List<Telephone> moblst=	vcard.getTelephoneNumbers();
			 for(Telephone tel: moblst)
			 { 
				 if(tel.getTypes().toString().contains("home"))
						 {
					 dispres.setWebamilPhoneHome(tel.getText()); 
						 }
				 else  if(tel.getTypes().toString().contains("work"))
						 {
					  dispres.setWebamilPhoneWork(tel.getText()); 
					 }
				 else if(tel.getTypes().toString().contains("cell"))
					 {
					 dispres.setWebamilPhoneMob( tel.getText()); 
					 }
			 	 else if(tel.getTypes().toString().contains("fax"))
			 	 	{
			 		dispres.setWebamilPhoneFax( tel.getText()); 
			 	 	}
				// System.out.println("!!!!!!!!!!!!!!!!!!!mob:"+tel.getText()+" type:"+tel.getTypes());
				 
			 }
			 
			 List<Address> addr= vcard.getAddresses();
			 for(Address ad: addr)
			 {
				 if(ad.getTypes().toString().contains("home"))
				 {
					 dispres.setWebamilAddrHome( ad.getStreetAddress() ); 
				 }
				 else if(ad.getTypes().toString().contains("work"))
				 {
					 dispres.setWebmailAddrWork(ad.getStreetAddress());
				 }
				 //System.out.println("Address:"+ad.getCountry()+" type:"+ad.getTypes());
				System.out.println("!!!!!!!!!!!!!!!!!!!Address:"+ad.getStreetAddress()+" type:"+ad.getTypes());
			 }
			 
			List<Url> urllst= vcard.getUrls();
			for(Url ulst: urllst)
			{
				dispres.setWebamilWebPage(ulst.getValue());
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return dispres;
	}
	
	
	
	
	
	
	
	
	
	
	

}
