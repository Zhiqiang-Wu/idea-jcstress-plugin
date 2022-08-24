package wzq.jcstress.plugin.configuration;

import com.intellij.execution.Location;
import com.intellij.execution.actions.ConfigurationContext;
import com.intellij.execution.configurations.ConfigurationFactory;
import com.intellij.execution.configurations.ConfigurationTypeUtil;
import com.intellij.execution.junit.JavaRunConfigurationProducerBase;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressConfigurationProducer extends JavaRunConfigurationProducerBase<JCStressRunConfiguration> {

    @Override
    protected boolean setupConfigurationFromContext(@NotNull JCStressRunConfiguration configuration, @NotNull ConfigurationContext context, @NotNull Ref<PsiElement> sourceElement) {
        Location location = context.getLocation();
        if (location == null) {
            return false;
        }

        PsiElement psiElement = location.getPsiElement();
        if (!(psiElement instanceof PsiClass psiClass)) {
            return false;
        }

        if (!psiClass.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")) {
            return false;
        }

        configuration.setName(psiClass.getName());
        configuration.setWorkingDirectory("$MODULE_WORKING_DIR$");
        configuration.setProgramParameters(psiClass.getName());

        return true;
    }

    @Override
    public boolean isConfigurationFromContext(@NotNull JCStressRunConfiguration configuration, @NotNull ConfigurationContext context) {
        Location location = context.getLocation();
        if (location == null) {
            return false;
        }

        PsiElement psiElement = location.getPsiElement();
        if (!(psiElement instanceof PsiClass psiClass)) {
            return false;
        }

        return true;
    }

    @Override
    public @NotNull ConfigurationFactory getConfigurationFactory() {
        JCStressConfigurationType configurationType = ConfigurationTypeUtil.findConfigurationType(JCStressConfigurationType.class);
        return configurationType.getConfigurationFactories()[0];
    }
}
