package hello;



import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;


@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	
	
	@Bean
	public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "countries")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema countriesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CountriesPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
		wsdl11Definition.setSchema(countriesSchema);
		return wsdl11Definition;
	}

	@Bean(name="documentFolder")
	public DefaultWsdl11Definition defaultWsdlDefinition(XsdSchema documentFileSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("FolderPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://edms.com/Folder");
		wsdl11Definition.setSchema(documentFileSchema);
		return wsdl11Definition;
	}

	
	@Bean(name="webmailFolder")
	public DefaultWsdl11Definition webmailfolderWsdlDefinition(XsdSchema webmailfolderSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailFolderPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Folder");
		wsdl11Definition.setSchema(webmailfolderSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailQuota")
	public DefaultWsdl11Definition webmailquotaWsdlDefinition(XsdSchema webmailQuotaSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailQuotaPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Quota");
		wsdl11Definition.setSchema(webmailQuotaSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailAuthentication")
	public DefaultWsdl11Definition webmailAuthWsdlDefinition(XsdSchema webmailAuthSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailAuthPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Authentication");
		wsdl11Definition.setSchema(webmailAuthSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailImapquota")
	public DefaultWsdl11Definition webmailImapquotaWsdlDefinition(XsdSchema webmailImapquotaSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailImapquotaPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Imapquota");
		wsdl11Definition.setSchema(webmailImapquotaSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailEmailcount")
	public DefaultWsdl11Definition webmailEmailcountWsdlDefinition(XsdSchema webmailEmailcountSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailcountPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Mailcount");
		wsdl11Definition.setSchema(webmailEmailcountSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailInbox")
	public DefaultWsdl11Definition webmailEmailinboxWsdlDefinition(XsdSchema webmailEmailinboxSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailinboxPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Mailinbox");
		wsdl11Definition.setSchema(webmailEmailinboxSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailSearch")
	public DefaultWsdl11Definition webmailEmailsearchWsdlDefinition(XsdSchema webmailEmailsearchSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailsearchPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Search");
		wsdl11Definition.setSchema(webmailEmailsearchSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailMoveTrash")
	public DefaultWsdl11Definition webmailEmailmovetrashWsdlDefinition(XsdSchema webmailEmailmovetrashSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailmovetrashPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Movetrash");
		wsdl11Definition.setSchema(webmailEmailmovetrashSchema);
		return wsdl11Definition;
	}
	
	/*@Bean(name="webmailMoveJunk")
	public DefaultWsdl11Definition webmailEmailmovejunkhWsdlDefinition(XsdSchema webmailEmailmovejunkSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailmovejunkPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Movejunk");
		wsdl11Definition.setSchema(webmailEmailmovejunkSchema);
		return wsdl11Definition;
	}*/
	
	@Bean(name="webmailFlagAction")
	public DefaultWsdl11Definition webmailEmailflagactionWsdlDefinition(XsdSchema webmailEmailflagactionSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailflagactionPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Flagaction");
		wsdl11Definition.setSchema(webmailEmailflagactionSchema);
		return wsdl11Definition;
	}
	
	
	
	@Bean(name="webmailSeenAction")
	public DefaultWsdl11Definition webmailEmailseenctionWsdlDefinition(XsdSchema webmailEmailseenactionSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailseenactionPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Seenaction");
		wsdl11Definition.setSchema(webmailEmailseenactionSchema);
		return wsdl11Definition;
	}
	
	
	
	@Bean(name="webmailReadAction")
	public DefaultWsdl11Definition webmailEmailreadactionWsdlDefinition(XsdSchema webmailEmailreadactionSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailreadactionPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Readaction");
		wsdl11Definition.setSchema(webmailEmailreadactionSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailContentDisplay")
	public DefaultWsdl11Definition webmailEmailcontentdisplayWsdlDefinition(XsdSchema webmailEmailcontentdisplaySchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailcontentdisplayPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Maildisplay");
		wsdl11Definition.setSchema(webmailEmailcontentdisplaySchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailAttachmentDownload")
	public DefaultWsdl11Definition webmailEmailattachmentdownloadWsdlDefinition(XsdSchema webmailEmailattachmentdownloadSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailattachmentdownloadPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Maildownload");
		wsdl11Definition.setSchema(webmailEmailattachmentdownloadSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailCompose")
	public DefaultWsdl11Definition webmailEmailcomposeWsdlDefinition(XsdSchema webmailEmailComposeSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailEmailcomposePort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Compose");
		wsdl11Definition.setSchema(webmailEmailComposeSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailLDAPAtt")
	public DefaultWsdl11Definition webmailLdapAttWsdlDefinition(XsdSchema webmailLdapAttSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailLdapAttPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/LdapAttribute");
		wsdl11Definition.setSchema(webmailLdapAttSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailLDAPModifyAtt")
	public DefaultWsdl11Definition webmailLdapModifyAttWsdlDefinition(XsdSchema webmailLdapModifyAttSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailLdapModifyAttPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/LdapModifyAttribute");
		wsdl11Definition.setSchema(webmailLdapModifyAttSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailContactCreate")
	public DefaultWsdl11Definition webmailContactCreateWsdlDefinition(XsdSchema webmailContactCreateSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailContactCreatePort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/CreateContact");
		wsdl11Definition.setSchema(webmailContactCreateSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailLdapUserDirectory")
	public DefaultWsdl11Definition webmailLdapUserDirectoryWsdlDefinition(XsdSchema webmailLdapUserDirectorySchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailLdapUserDirectoryPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/LdapUserDirectory");
		wsdl11Definition.setSchema(webmailLdapUserDirectorySchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailLdapDirectory")
	public DefaultWsdl11Definition webmailLdapDirectoryWsdlDefinition(XsdSchema webmailLdapDirectorySchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailLdapDirectoryPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/LdapDirectory");
		wsdl11Definition.setSchema(webmailLdapDirectorySchema);
		return wsdl11Definition;
	}
	
	@Bean(name="calendar")
	public DefaultWsdl11Definition defaultWsdlCalendarHistDefinition(XsdSchema calendarSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("CalendarPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/calendar");
		wsdl11Definition.setSchema(calendarSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailAttachment")
	public DefaultWsdl11Definition defaultWsdlAttachmentDefinition(XsdSchema attachmentSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("AttachmentPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/MailAttach");
		wsdl11Definition.setSchema(attachmentSchema);
		return wsdl11Definition;
	}
	
	@Bean(name="webmailTask")
	public DefaultWsdl11Definition webmailTaskWsdlDefinition(XsdSchema webmailTaskSchema){
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WebmailTaskPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://webmail.com/Task");
		wsdl11Definition.setSchema(webmailTaskSchema);
		return wsdl11Definition;
	}
	
	
	
	@Bean
	public XsdSchema calendarSchema() {
		return new SimpleXsdSchema(new ClassPathResource("calendar.xsd"));
	}
	
	@Bean
	public XsdSchema countriesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("countries.xsd"));
	}
	@Bean
	public XsdSchema documentFileSchema() {
		return new SimpleXsdSchema(new ClassPathResource("documentFolder.xsd"));
	}
	@Bean
	public XsdSchema webmailfolderSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailFolder.xsd"));
	}
	@Bean
	public XsdSchema webmailQuotaSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailQuota.xsd"));
	}
	@Bean
	public XsdSchema webmailAuthSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailAuthentication.xsd"));
	}
	@Bean
	public XsdSchema webmailImapquotaSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailImapquota.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailcountSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailEmailcount.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailinboxSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailInbox.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailsearchSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailSearch.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailmovetrashSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailMoveTrash.xsd"));
	}
	/*@Bean
	public XsdSchema webmailEmailmovejunkSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailMoveJunk.xsd"));
	}*/
	@Bean
	public XsdSchema webmailEmailflagactionSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailFlagAction.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailreadactionSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailReadAction.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailseenactionSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailSeenAction.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailcontentdisplaySchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailContentDisplay.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailattachmentdownloadSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailAttachmentDownload.xsd"));
	}
	@Bean
	public XsdSchema webmailEmailComposeSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailCompose.xsd"));
	}
	@Bean
	public XsdSchema webmailLdapAttSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailLDAPAtt.xsd"));
	}
	@Bean
	public XsdSchema webmailLdapModifyAttSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailLDAPModifyAtt.xsd"));
	}
	@Bean
	public XsdSchema webmailContactCreateSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailContactCreate.xsd"));
	}
	@Bean
	public XsdSchema webmailLdapUserDirectorySchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailLdapUserDirectory.xsd"));
	}
	@Bean
	public XsdSchema webmailLdapDirectorySchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailLdapDirectory.xsd"));
	} 
	@Bean
	public XsdSchema attachmentSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailAttachment.xsd"));
	}
	@Bean
	public XsdSchema webmailTaskSchema() {
		return new SimpleXsdSchema(new ClassPathResource("webmailTask.xsd"));
	}
}
