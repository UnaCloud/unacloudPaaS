package unacloud.paas.back.execution;

public class InstallPackageModule extends PuppetModuleInstance{

	public InstallPackageModule(String moduleName) {
		super(moduleName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		String h= "\tpackage { '"+getModuleName()+"' :\n";
		h+="\t\tensure\t=> installed,\n";
		h+="\t}";
		return h;
	}
}
