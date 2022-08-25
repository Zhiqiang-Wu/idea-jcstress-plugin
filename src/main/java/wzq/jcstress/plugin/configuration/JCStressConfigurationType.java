package wzq.jcstress.plugin.configuration;

import com.intellij.execution.configurations.JavaRunConfigurationModule;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.SimpleConfigurationType;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NotNullLazyValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressConfigurationType extends SimpleConfigurationType {

    public JCStressConfigurationType() {
        super("jcstress-id", "JCStress", "Configuration to run a jcstress test", NotNullLazyValue.createValue(() -> AllIcons.Actions.ProfileYellow));
    }

    @Override
    public @NotNull RunConfiguration createTemplateConfiguration(@NotNull Project project) {
        return new JCStressRunConfiguration("jcstress", new JavaRunConfigurationModule(project, false), this);
    }
}
