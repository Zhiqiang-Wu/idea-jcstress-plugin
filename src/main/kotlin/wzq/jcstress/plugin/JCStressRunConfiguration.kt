package wzq.jcstress.plugin

import com.intellij.execution.Executor
import com.intellij.execution.JavaRunConfigurationBase
import com.intellij.execution.ShortenCommandLine
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.JavaRunConfigurationModule
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.RunProfileState
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.openapi.module.Module
import com.intellij.openapi.options.SettingsEditor
import org.jdom.Element

/**
 * @author 吴志强
 */
class JCStressRunConfiguration(
    name: String,
    javaRunConfigurationModule: JavaRunConfigurationModule,
    configurationFactory: ConfigurationFactory,
) : JavaRunConfigurationBase(name, javaRunConfigurationModule, configurationFactory) {
    private var vmParameters: String? = null
    private var alternativeJrePathEnabled = false
    private var alternativeJrePath: String? = null
    private var programParameters: String? = null
    private var workingDirectory: String? = null
    private var envs: Map<String?, String?> = hashMapOf()
    private var passParentEnvs = false
    private var shortenCommandLine: ShortenCommandLine? = null
    var jcstressClass: String? = null

    override fun getValidModules(): Collection<Module?>? = null

    override fun getState(
        executor: Executor,
        environment: ExecutionEnvironment,
    ): RunProfileState? = JCStressCommandLineState(environment, this)

    override fun getConfigurationEditor(): SettingsEditor<out RunConfiguration?> = JCStressSettingsEditor()

    override fun setVMParameters(vmParameters: String?) {
        this.vmParameters = vmParameters
    }

    override fun getVMParameters(): String? = this.vmParameters

    override fun isAlternativeJrePathEnabled(): Boolean = this.alternativeJrePathEnabled

    override fun setAlternativeJrePathEnabled(alternativeJrePathEnabled: Boolean) {
        this.alternativeJrePathEnabled = alternativeJrePathEnabled
    }

    override fun getAlternativeJrePath(): String? = this.alternativeJrePath

    override fun setAlternativeJrePath(alternativeJrePath: String?) {
        this.alternativeJrePath = alternativeJrePath
    }

    override fun getRunClass(): String? = "org.openjdk.jcstress.Main"

    override fun getPackage(): String? = null

    override fun setProgramParameters(programParameters: String?) {
        this.programParameters = programParameters
    }

    override fun getProgramParameters(): String? = this.programParameters

    override fun setWorkingDirectory(workingDirectory: String?) {
        this.workingDirectory = workingDirectory
    }

    override fun getWorkingDirectory(): String? = this.workingDirectory

    override fun setEnvs(envs: Map<String?, String?>) {
        this.envs = envs
    }

    override fun getEnvs(): Map<String?, String?> = this.envs

    override fun setPassParentEnvs(passParentEnvs: Boolean) {
        this.passParentEnvs = passParentEnvs
    }

    override fun isPassParentEnvs(): Boolean = this.passParentEnvs

    override fun getShortenCommandLine(): ShortenCommandLine? = this.shortenCommandLine

    override fun setShortenCommandLine(shortenCommandLine: ShortenCommandLine?) {
        this.shortenCommandLine = shortenCommandLine
    }

    override fun readExternal(element: Element) {
        super.readExternal(element)
        this.vmParameters = element.getAttributeValue("vmParameters")
        this.programParameters = element.getAttributeValue("programParameters")
        this.workingDirectory = element.getAttributeValue("workingDirectory")
        this.jcstressClass = element.getAttributeValue("jcstressClass")
    }

    override fun writeExternal(element: Element) {
        super.writeExternal(element)
        if (this.vmParameters != null) {
            element.setAttribute("vmParameters", this.vmParameters)
        }
        if (this.programParameters != null) {
            element.setAttribute("programParameters", this.programParameters)
        }
        if (this.workingDirectory != null) {
            element.setAttribute("workingDirectory", this.workingDirectory)
        }
        if (this.jcstressClass != null) {
            element.setAttribute("jcstressClass", this.jcstressClass)
        }
    }
}
