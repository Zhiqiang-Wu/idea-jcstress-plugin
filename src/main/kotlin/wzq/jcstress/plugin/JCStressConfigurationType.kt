package wzq.jcstress.plugin

import com.intellij.execution.configurations.JavaRunConfigurationModule
import com.intellij.execution.configurations.RunConfiguration
import com.intellij.execution.configurations.SimpleConfigurationType
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.NotNullFactory
import com.intellij.openapi.util.NotNullLazyValue
import wzq.jcstress.plugin.configuration.Icons

/**
 * @author 吴志强
 */
class JCStressConfigurationType :
    SimpleConfigurationType(
        "jcstress",
        "JCStress",
        "Configuration to run a jcstress test",
        NotNullLazyValue.createValue(NotNullFactory { Icons.CONFIG }),
    ) {
    override fun createTemplateConfiguration(project: Project): RunConfiguration =
        JCStressRunConfiguration("jcstress", JavaRunConfigurationModule(project, false), this)
}
