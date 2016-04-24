package br.com.simple.jdbc.dao;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javax.naming.NamingException;

import br.com.vitulus.simple.jdbc.dao.AbstractDao;
import br.ueg.openodonto.controle.ManterPaciente;
import br.ueg.openodonto.controle.context.ApplicationContext;
import br.ueg.openodonto.dominio.Paciente;
import br.ueg.openodonto.dominio.Telefone;
import br.ueg.openodonto.dominio.Usuario;
import br.ueg.openodonto.dominio.constante.TiposTelefone;
import br.ueg.openodonto.dominio.constante.TiposUF;
import br.ueg.openodonto.servico.busca.InputField;
import br.ueg.openodonto.servico.busca.ResultFacade;
import br.ueg.openodonto.servico.busca.SearchFilter;
import br.ueg.openodonto.visao.ApplicationView;


public class ManterPacienteTest {

	private static char[] CONSOANTES = {'b','c','d','f','g','h','j','k','l','m','n','p','q','r','s','t','v','x','y','z','w'};
	private static char[] VOGAIS = {'a','e','i','o','u'};
	private static char[] NUMBERS = {'0','1','2','3','4','5','6','7','8','9'};
	private static int MaxEstados = TiposUF.values().length;
	private static int MaxTiposTel = TiposTelefone.values().length;
	
	static volatile int createTimes;
	static volatile int recuperarTimes;
	static volatile int updateTimes;
	static volatile int deleteTimes;
	private static int genTimes = 90000; // Um milhão de vezes
	private static int users    = 100;  // Quantidade de usuários Simulados
	
	
	static volatile long timeCreate;
	static volatile long timeRecuperar;
	static volatile long timeUpdate;
	static volatile long timeDelete;
	static volatile long timeGenerateData;
	static volatile boolean isPrinted    = false;  // Quantidade de usuários Simulados
	
	private ManterPaciente manterPaciente;
	private UnitTestContext context;
	
	
	public ManterPacienteTest() {
		manterPaciente = new ManterPaciente(){
			private static final long serialVersionUID = -9039185309165031309L;

			public void makeView(Map<String, String> params){
				this.setView(new UnitTestView(params));
			}
		};
		manterPaciente.setContext(context = new UnitTestContext());
	}
	

	
	public static void main(String[] args) throws NamingException {    
	    
		TestSetup.setup();
		
		long timeGenerateDataStart = System.currentTimeMillis();
		List<Thread> bootUsers = new ArrayList<Thread>();
		List<Job>      jobs = buildJob();
		for(int i = 0 ; i < users ; i++){
			bootUsers.add(new Thread(new Stress(jobs.get(i))));
		}
		timeGenerateData = System.currentTimeMillis() - timeGenerateDataStart;
		Iterator<Thread> usersIterator = bootUsers.iterator();
		long timeStart = System.currentTimeMillis();
		while(usersIterator.hasNext()){
			usersIterator.next().start();
		}
		usersIterator = bootUsers.iterator();
		while(usersIterator.hasNext()){
			try {
				usersIterator.next().join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}		
		printResults(timeStart,System.currentTimeMillis());
		System.out.println(Thread.currentThread().getName() + " [TERMINOU]");
	}
	
	private static List<Job> buildJob(){
		List<Job> jobs = new ArrayList<Job>();
		for(int i = 0 ;i  < users; i++){
			Queue<Paciente> jobData = new LinkedList<Paciente>();
			for(int j = 0 ; j < genTimes/users ; j++){
				jobData.add(generatePaciente());
			}
			jobs.add(new Job(jobData));
		}
		return jobs;
	}
	
	public static void printResults(long start, long end){
		System.out.format("%-30s", "Operação");
		System.out.format("%-30s", "Tempo");
		System.out.format("%-30s", "CRUD").append("\n");
		
		System.out.format("%-30s", "Criação Objetos");
		System.out.format("%-30d", timeGenerateData);
		System.out.format("%-30d", genTimes).append("\n");
		
		System.out.format("%-30s", "Inserção");
		System.out.format("%-30d", timeCreate);
		System.out.format("%-30d", createTimes).append("\n");
		
		System.out.format("%-30s", "Recuperação");
		System.out.format("%-30d", timeRecuperar);
		System.out.format("%-30d", recuperarTimes).append("\n");
		
		System.out.format("%-30s", "Atualização");
		System.out.format("%-30d", timeUpdate);
		System.out.format("%-30d", updateTimes).append("\n");
		
		System.out.format("%-30s", "Remoção");
		System.out.format("%-30d", timeDelete);
		System.out.format("%-30d", deleteTimes).append("\n");
		
		System.out.format("%-30s", "Total Linear BD");
		System.out.format("%-30d", timeCreate+timeRecuperar+timeUpdate+timeDelete);
		System.out.format("%-30d", createTimes+recuperarTimes+updateTimes+deleteTimes).append("\n").append("\n");
		
		System.out.format("%-30s", "Operação");
		System.out.format("%-30s", "Tempo");
		System.out.format("%-30s", "Total").append("\n");
		
		System.out.format("%-30s","SQL : ");
		System.out.format("%-30d",end - start);
		System.out.format("%-30d",AbstractDao.times).append("\n");	
	}
	
	private static Paciente generatePaciente(){
		Paciente paciente = new Paciente();
		paciente.setCidade(generateWord(5,10, 1,CONSOANTES,VOGAIS));
		paciente.setCpf(generateWord(11,11, 1,NUMBERS));
		paciente.setDataInicioTratamento(new Date(System.currentTimeMillis()));
		paciente.setDataRetorno(new Date(System.currentTimeMillis()));
		paciente.setDataTerminoTratamento(new Date(System.currentTimeMillis()));
		paciente.setEmail(generateWord(5,10, 1,CONSOANTES,VOGAIS).trim() + "@" + generateWord(5,10, 1,CONSOANTES,VOGAIS).trim() + ".com");
		paciente.setEndereco(generateWord(5,10, 1,CONSOANTES,VOGAIS) + " N " + generateWord(2,5, 1,NUMBERS));
		paciente.setEstado(TiposUF.values()[generateNumber(MaxEstados)]);
		paciente.setNome(generateWord(5,15,4,CONSOANTES,VOGAIS));
		paciente.setObservacao(generateWord(5,10,50,CONSOANTES,VOGAIS));
		paciente.setReferencia(generateWord(5,15,4,CONSOANTES,VOGAIS));
		paciente.setResponsavel(generateWord(5,15,4,CONSOANTES,VOGAIS));
		paciente.setTelefone(generateTelefones());
		return paciente;
	}
	
	private static List<Telefone> generateTelefones(){
		int qTels = generateNumber(10);
		List<Telefone> telefones = new ArrayList<Telefone>();
		for(int i = 0;  i < qTels ; i++){
			Telefone e = new Telefone();
			e.setNumero(generateWord(10,15, 1,NUMBERS));
			e.setTipoTelefone(TiposTelefone.values()[generateNumber(MaxTiposTel)]);
			telefones.add(e);
		}
		return telefones;
	}
	
	public static String generateWord(int min,int max,int words,char[]... domain){
		StringBuilder stb = new StringBuilder();		
		for(int i = 0 ; i < words ; i++){
			int rLen = generateNumber(max);
			rLen = rLen < min ? min : rLen; 
			for (int j = 0; j < rLen; j++) {
				int rDomain = generateNumber(domain.length);
				int rChar = generateNumber(domain[rDomain].length);
				stb.append(domain[rDomain][rChar]);
			}
			if(i < words){
				stb.append(" ");
			}
		}
		return stb.toString();
	}
	
	private static int generateNumber(int max){
		return (int)(Math.random() * 1000) % max;
	}
	
	public void create(){
		manterPaciente.acaoSalvar();
		manterPaciente.setContext(context);
		createTimes++;
	}
	
	@SuppressWarnings("unchecked")
	public void recuperar(Long id){
		try{
			Iterator<SearchFilter> iterator = manterPaciente.getSearch().getSearchable().getFilters().iterator();
			SearchFilter filter;
			while(iterator.hasNext()){
				filter = iterator.next();
				if(filter.getName().equals("idFilter")){
					InputField<String> input = (InputField<String>)filter.getField().getInputFields().get(0);
					input.setValue(id.toString());
					break;
				}
			}
			manterPaciente.getSearch().search();
			ResultFacade selected = manterPaciente.getSearch().getResults().get(0); 
			manterPaciente.getSearch().setSelected(selected);
		}finally{
		    recuperarTimes++;
		}
	}
	
	public void update(){
		manterPaciente.acaoSalvar();
		manterPaciente.setContext(context);
		updateTimes++;
	}
	
	public void delete(){
		manterPaciente.acaoRemoverSim();
		manterPaciente.setContext(context);
		deleteTimes++;
	}
	
	public ManterPaciente getManterPaciente() {
		return manterPaciente;
	}
	
	public UnitTestContext getContext() {
		return context;
	}
}

class Stress implements Runnable{

	private Job job;
	
	public Stress(Job job){
		this.job = job;
	}
	
	@Override
	public void run() {
		while(job.hasData()){
			Paciente paciente = job.getPaciente();
			doCrud(paciente);
		}
		System.out.println(Thread.currentThread().getName() + " [TERMINOU]");
	}
	
	private void doCrud(Paciente paciente){
		long parcial = 0;
		ManterPacienteTest main = new ManterPacienteTest();	
		main.getManterPaciente().setBackBean(paciente);
		
		
		parcial = System.currentTimeMillis();
		main.create();
		ManterPacienteTest.timeCreate += System.currentTimeMillis() - parcial;	
		
		/*
		parcial = System.currentTimeMillis();
		main.recuperar(paciente.getCodigo());
		ManterPacienteTest.timeRecuperar += System.currentTimeMillis() - parcial;
		
		parcial = System.currentTimeMillis();			
		main.update();
		main.getManterPaciente().setBackBean(paciente);
		ManterPacienteTest.timeUpdate += System.currentTimeMillis() - parcial;
		
		parcial = System.currentTimeMillis();
		main.delete();
		ManterPacienteTest.timeDelete += System.currentTimeMillis() - parcial;
		*/
		
	}
	
}


class UnitTestContext implements ApplicationContext{

	private static final long serialVersionUID = -6670444279669734069L;
	private Map<String , Object> values;
	
	public UnitTestContext() {
		this.values = new HashMap<String, Object>();
	}
	
	public Map<String, Object> getValues() {
		return values;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public <T> T getAttribute(String name, Class<T> classe) {
		Object value = values.get(name);
		if(value != null){
			return (T)value;
		}
		return null;
	}

	@Override
	public String getParameter(String name) {
		Object value = values.get(name);
		if(value != null){
			return String.valueOf(value);
		}
		return null;
	}

	@Override
	public Usuario getUsuarioSessao() {
		return null;
	}

	@Override
	public Map<String, Object> getAttributes() {
		return values;
	}

	@Override
	public void removeAttribute(String name) {
		values.remove(name);
	}

	@Override
	public void addAttribute(String name, Object value) {
		values.put(name, value);
	}

	@Override
	public Locale getClientLocale() {
		return Locale.getDefault();
	}	
}


class UnitTestView implements ApplicationView {

	private static final long serialVersionUID = -5957572496863874257L;
	private static ResourceBundle resourceBundle;
	private Map<String, String> params;
	
	static{
		resourceBundle = ResourceBundle.getBundle("br.ueg.openodonto.visao.i18n.messages_pt");
	}
	
	public UnitTestView(Map<String, String> params) {
		this.params = params;
	}
	
	@Override
	public void addLocalDynamicMenssage(String msg, String target,
			boolean targetParam) {
		
	}

	@Override
	public void addLocalMessage(String key, String target, boolean targetParam) {
		
	}

	@Override
	public void addResourceDynamicMenssage(String msg, String target) {
		
	}

	@Override
	public void addResourceMessage(String key, String target) {
	
	}

	@Override
	public boolean getDisplayMessages() {
		return false;
	}

	@Override
	public boolean getDisplayPopUp() {
		return false;
	}

	@Override
	public Map<String, String> getProperties() {
		return params;
	}

	@Override
	public String getPopUpMsg() {
		return null;
	}

	@Override
	public void refresh() {
		
	}

	@Override
	public void showAction() {
		
	}

	@Override
	public void showOut() {
		
	}

	@Override
	public void showPopUp(String msg) {
		
	}

	@Override
	public String getMessageFromResource(String name) {
		return resourceBundle.getString(name);
	}
	
}

class Job{
	
	private Queue<Paciente> jobData;
	
	public Job(Queue<Paciente> jobData) {
		this.jobData = jobData;
	}
	
	public boolean hasData(){
		return !jobData.isEmpty();
	}
	
	public Paciente getPaciente(){
		return jobData.poll();
	}
	
}
