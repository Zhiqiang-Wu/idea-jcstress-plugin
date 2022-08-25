package wzq.jcstress.plugin.configuration;

import com.intellij.execution.lineMarker.RunLineMarkerContributor;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.psi.PsiElement;
import com.intellij.util.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.uast.UClass;
import org.jetbrains.uast.UElement;
import org.jetbrains.uast.UastUtils;

/**
 * @author 吴志强
 * @date 2022/8/24
 */
public class JCStressRunLineMarkerContributor extends RunLineMarkerContributor {

    @Override
    public @Nullable Info getInfo(@NotNull PsiElement psiElement) {
        UElement uElement = UastUtils.getUParentForIdentifier(psiElement);
        if (uElement instanceof UClass uClass && uClass.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")) {
            return new Info(AllIcons.Actions.ProfileYellow, new TooltipProvider(), ActionManager.getInstance().getAction("RunClass"));
        }
        return null;
    }

    private static class TooltipProvider implements Function<PsiElement, String> {

        @Override
        public String fun(PsiElement psiElement) {
            return "";
        }
    }
}
