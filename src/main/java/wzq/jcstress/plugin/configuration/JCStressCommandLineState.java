package wzq.jcstress.plugin.configuration;

import com.intellij.execution.application.BaseJavaApplicationCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import org.jetbrains.annotations.NotNull;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressCommandLineState extends BaseJavaApplicationCommandLineState<JCStressRunConfiguration> {

    public JCStressCommandLineState(ExecutionEnvironment environment, @NotNull JCStressRunConfiguration configuration) {
        super(environment, configuration);
    }

    @Override
    protected JavaParameters createJavaParameters() {
        JavaParameters parameters = new JavaParameters();
        JavaParametersUtil.configureConfiguration(parameters, this.myConfiguration);
        parameters.setMainClass("org.openjdk.jcstress.Main");
        return parameters;
    }
}
