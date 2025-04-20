package wzq.jcstress.plugin

import com.intellij.execution.JavaExecutionUtil
import com.intellij.execution.actions.ConfigurationContext
import com.intellij.execution.configurations.ConfigurationFactory
import com.intellij.execution.configurations.ConfigurationTypeUtil.findConfigurationType
import com.intellij.execution.junit.JavaRunConfigurationProducerBase
import com.intellij.openapi.util.Ref
import com.intellij.psi.PsiElement
import org.jetbrains.uast.UClass
import org.jetbrains.uast.findContaining
import wzq.jcstress.plugin.JCStressConfigurationType
import kotlin.jvm.java

/**
 * @author 吴志强
 */
class JCStressConfigurationProducer : JavaRunConfigurationProducerBase<JCStressRunConfiguration>() {
    override fun setupConfigurationFromContext(
        configuration: JCStressRunConfiguration,
        context: ConfigurationContext,
        sourceElement: Ref<PsiElement?>,
    ): Boolean {
        val location = context.location ?: return false

        val uClass = location.psiElement.findContaining(UClass::class.java) ?: return false

        if (!uClass.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")) {
            return false
        }

        sourceElement.set(uClass)

        this.setupConfigurationModule(context, configuration)

        val originalModule = configuration.configurationModule.module
        configuration.restoreOriginalModule(originalModule)
        configuration.name = uClass.getName() ?: ""
        configuration.workingDirectory = "\$MODULE_WORKING_DIR$"
        configuration.jcstressClass = uClass.getQualifiedName()
        configuration.programParameters = "-t ${uClass.getQualifiedName()} -v"

        return true
    }

    override fun isConfigurationFromContext(
        configuration: JCStressRunConfiguration,
        context: ConfigurationContext,
    ): Boolean {
        val location = context.location ?: return false

        val uClass = location.psiElement.findContaining(UClass::class.java) ?: return false

        if (!uClass.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")) {
            return false
        }

        if (configuration.jcstressClass != uClass.qualifiedName) {
            return false
        }

        val module = JavaExecutionUtil.stepIntoSingleClass(location).module ?: return false
        val originalModule = configuration.configurationModule.module

        if (module != originalModule) {
            return false
        }

        this.setupConfigurationModule(context, configuration)
        configuration.restoreOriginalModule(originalModule)

        return true
    }

    override fun getConfigurationFactory(): ConfigurationFactory {
        val configurationType = findConfigurationType(JCStressConfigurationType::class.java)
        return configurationType.configurationFactories[0]
    }
}
