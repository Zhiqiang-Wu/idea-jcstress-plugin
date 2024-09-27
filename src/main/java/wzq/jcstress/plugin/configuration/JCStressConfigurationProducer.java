package wzq.jcstress.plugin.configuration;

import java.util.Objects;

import com.intellij.execution.JavaExecutionUtil;
import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.execution.junit.JavaRunConfigurationProducerBase;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UastUtils;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressConfigurationProducer
        extends JavaRunConfigurationProducerBase<JCStressRunConfiguration> {

    @Override
    protected boolean setupConfigurationFromContext(
            @NotNull JCStressRunConfiguration configuration,
            @NotNull ConfigurationContext context,
            @NotNull Ref<PsiElement> sourceElement) {
        Location location = context.getLocation();
        if (location == null) {
            return false;
        }

        UClass uClass = UastUtils.findContaining(location.getPsiElement(), UClass.class);
        if (uClass == null) {
            return false;
        }
        if (!uClass.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")) {
            return false;
        }

        sourceElement.set(uClass);

        this.setupConfigurationModule(context, configuration);

        Module originalModule = configuration.getConfigurationModule().getModule();
        configuration.restoreOriginalModule(originalModule);
        configuration.setName(uClass.getName());
        configuration.setWorkingDirectory("$MODULE_WORKING_DIR$");
        configuration.setJCStressClass(uClass.getQualifiedName());
        configuration.setProgramParameters(String.format("-t %s -v", uClass.getQualifiedName()));

        return true;
    }

    @Override
    public boolean isConfigurationFromContext(
            @NotNull JCStressRunConfiguration configuration, @NotNull ConfigurationContext context) {
        Location location = context.getLocation();
        if (location == null) {
            return false;
        }

        UClass uClass = UastUtils.findContaining(location.getPsiElement(), UClass.class);
        if (uClass == null) {
            return false;
        }
        if (!uClass.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")) {
            return false;
        }

        if (!Objects.equals(uClass.getQualifiedName(), configuration.getJCStressClass())) {
            return false;
        }

        location = JavaExecutionUtil.stepIntoSingleClass(location);
        Module originalModule = configuration.getConfigurationModule().getModule();
        if (location.getModule() == null || !location.getModule().equals(originalModule)) {
            return false;
        }

        this.setupConfigurationModule(context, configuration);
        configuration.restoreOriginalModule(originalModule);

        return true;
    }

    @Override
    public @NotNull ConfigurationFactory getConfigurationFactory() {
        JCStressConfigurationType configurationType =
                ConfigurationTypeUtil.findConfigurationType(JCStressConfigurationType.class);
        return configurationType.getConfigurationFactories()[0];
    }
}
