package wzq.jcstress.plugin

import com.intellij.execution.lineMarker.ExecutorAction
import com.intellij.execution.lineMarker.RunLineMarkerContributor
import com.intellij.psi.PsiElement
import org.jetbrains.uast.UClass
import org.jetbrains.uast.getUParentForIdentifier

/**
 * @author 吴志强
 */
class JCStressRunLineMarkerContributor : RunLineMarkerContributor() {
    override fun getInfo(psiElement: PsiElement): Info? {
        val uElement = getUParentForIdentifier(psiElement)
        if (uElement is UClass &&
            uElement.hasAnnotation("org.openjdk.jcstress.annotations.JCStressTest")
        ) {
            val actions = ExecutorAction.getActions(1)

            return Info(Icons.RUN, actions, null)
        }
        return null
    }
}
