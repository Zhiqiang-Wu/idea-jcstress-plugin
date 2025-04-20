package wzq.jcstress.plugin

import com.intellij.execution.application.BaseJavaApplicationCommandLineState
import com.intellij.execution.configurations.JavaParameters
import com.intellij.execution.runners.ExecutionEnvironment
import com.intellij.execution.util.JavaParametersUtil
import com.intellij.openapi.roots.ModuleRootManager
import com.intellij.openapi.roots.ProjectRootManager

/**
 * @author 吴志强
 */
class JCStressCommandLineState(
    environment: ExecutionEnvironment,
    configuration: JCStressRunConfiguration,
) : BaseJavaApplicationCommandLineState<JCStressRunConfiguration>(environment, configuration) {
    override fun createJavaParameters(): JavaParameters? {
        val parameters = JavaParameters()

        JavaParametersUtil.configureConfiguration(parameters, this.myConfiguration)

        parameters.mainClass = "org.openjdk.jcstress.Main"

        var classpathType =
            JavaParametersUtil.getClasspathType(
                this.myConfiguration.configurationModule,
                this.myConfiguration.jcstressClass ?: "",
                true,
            )
        classpathType =
            if (JavaParameters.JDK_AND_CLASSES == classpathType) {
                JavaParameters.CLASSES_ONLY
            } else if (JavaParameters.JDK_AND_CLASSES_AND_TESTS == classpathType) {
                JavaParameters.CLASSES_AND_TESTS
            } else {
                classpathType
            }

        JavaParametersUtil.configureModule(this.myConfiguration.configurationModule, parameters, classpathType, null)

        if (parameters.jdk == null) {
            val module = this.myConfiguration.configurationModule.module
            val jdk =
                if (module == null) {
                    ProjectRootManager.getInstance(this.myConfiguration.project).projectSdk
                } else {
                    ModuleRootManager.getInstance(module).sdk
                }
            parameters.jdk = jdk
        }

        return parameters
    }
}
