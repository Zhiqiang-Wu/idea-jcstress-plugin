package wzq.jcstress.plugin.configuration;

import com.intellij.execution.CantRunException;
import com.intellij.execution.application.BaseJavaApplicationCommandLineState;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.runners.ExecutionEnvironment;
import com.intellij.execution.util.JavaParametersUtil;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.roots.ProjectRootManager;
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
    protected JavaParameters createJavaParameters() throws CantRunException {
        JavaParameters parameters = new JavaParameters();

        JavaParametersUtil.configureConfiguration(parameters, this.myConfiguration);

        parameters.setMainClass("org.openjdk.jcstress.Main");

        int classpathType = JavaParametersUtil.getClasspathType(
                this.myConfiguration.getConfigurationModule(),
                this.myConfiguration.getJCStressClass(),
                true);
        classpathType = switch (classpathType) {
            case JavaParameters.JDK_AND_CLASSES -> JavaParameters.CLASSES_ONLY;
            case JavaParameters.JDK_AND_CLASSES_AND_TESTS -> JavaParameters.CLASSES_AND_TESTS;
            default -> classpathType;
        };
        JavaParametersUtil.configureModule(this.myConfiguration.getConfigurationModule(), parameters, classpathType, null);

        if (parameters.getJdk() == null) {
            // 没有设置jdk的情况下优先使用模块jdk，没有设置模块jdk的情况下使用项目jdk
            Module module = this.myConfiguration.getConfigurationModule().getModule();
            Sdk jdk;
            if (module == null) {
                jdk = ProjectRootManager.getInstance(this.myConfiguration.getProject()).getProjectSdk();
            } else {
                jdk = ModuleRootManager.getInstance(module).getSdk();
            }
            parameters.setJdk(jdk);
        }

        return parameters;
    }
}
