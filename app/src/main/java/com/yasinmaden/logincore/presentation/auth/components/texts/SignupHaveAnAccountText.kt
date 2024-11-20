package com.yasinmaden.logincore.presentation.auth.components.texts

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withLink
import com.yasinmaden.logincore.presentation.auth.signup.SignUpContract
import com.yasinmaden.logincore.common.Constants

@Composable
fun SignupHaveAnAccountText(
    onAction: (SignUpContract.UiAction) -> Unit
){
    Text(
        text = buildAnnotatedString {
            append("Already have an account? ")
            withLink(
                link = LinkAnnotation.Clickable(
                    tag = Constants.ClickableLink.SignIn.tag,
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            fontWeight = FontWeight.Bold
                        )
                    ),
                    linkInteractionListener = { onAction(SignUpContract.UiAction.OnSignInClick) }
                )
            ) {
                append("Sign In")
            }
        },
    )
}