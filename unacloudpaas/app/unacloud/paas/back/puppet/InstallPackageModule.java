package unacloud.paas.back.puppet;

public class InstallPackageModule extends PuppetModuleInstance{

	public InstallPackageModule(String moduleName) {
		super(moduleName);
	}

	@Override
	public String toString() {
		String h= "\tpackage { '"+getModuleName()+"' :\n";
		h+="\t\tensure\t=> installed,\n";
		h+="\t}";
		return h;
	}
}
