package com.av.movie.presentation.screen.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.av.avmovie.R
import com.av.movie.dataTest.GLADIATOR_II
import com.av.movie.dataTest.getFullPosterPath
import com.av.movie.ui.theme.Blue90
import com.av.movie.ui.theme.Cyan90
import com.av.movie.ui.theme.Grey10
import com.av.movie.ui.theme.LightGrey30

@Composable
fun AuthenticationScreen() {
    Scaffold(
        containerColor = Grey10
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AsyncImage(
                model = getFullPosterPath(GLADIATOR_II.posterPath),
                contentDescription = "background",
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Grey10),
                            startY = size.height / 10f,
                            endY = size.height
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
                        }
                    },
                contentScale = ContentScale.FillWidth,
                error = painterResource(id = R.drawable.ic_launcher_background),
                placeholder = painterResource(id = R.drawable.ic_launcher_background)
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(PaddingValues(bottom = 16.dp))
                    .align(Alignment.BottomCenter)
            ) {
                SignUpSection()
            }
        }
    }
}

@Composable
fun SignUpSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Sign Up",
            style = TextStyle(
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        var emailText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = emailText,
            onValueChange = { emailText = it },
            shape = RoundedCornerShape(50),
            placeholder = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "email",
                    modifier = Modifier.padding(start = 12.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        var passwordText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            shape = RoundedCornerShape(50),
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "password",
                    modifier = Modifier.padding(start = 12.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        var repeatPasswordText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = repeatPasswordText,
            onValueChange = { repeatPasswordText = it },
            shape = RoundedCornerShape(50),
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "password",
                    modifier = Modifier.padding(start = 12.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        CommonButton(
            text = "Sign Up",
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(128.dp))

        Text(
            text = "Already have an account? Log In",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
    }
}

@Composable
fun LoginMethodSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Introduction()

        Spacer(Modifier.height(32.dp))

        LoginMethods(
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        CommonButton(
            text = "Sign up",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun CommonButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val buttonBrush = Brush.linearGradient(
        colors = listOf(
            Cyan90,
            Blue90
        )
    )

    Button(
        onClick = onClick,
        modifier = modifier
            .background(
                brush = buttonBrush,
                shape = RoundedCornerShape(10.dp)
            )
        ,
        shape = RoundedCornerShape(10.dp),
        colors = buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun LoginSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Log In",
            style = TextStyle(
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(32.dp))

        var emailText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = emailText,
            onValueChange = { emailText = it },
            shape = RoundedCornerShape(50),
            placeholder = {
                Text(text = "Email")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "email",
                    modifier = Modifier.padding(start = 12.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        var passwordText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = passwordText,
            onValueChange = { passwordText = it },
            shape = RoundedCornerShape(50),
            placeholder = {
                Text(text = "Password")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "password",
                    modifier = Modifier.padding(start = 12.dp)
                )
            },
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        CommonButton(
            text = "Log In",
            modifier = Modifier
                .fillMaxWidth()
        )


        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = "Forgot password?",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp
                )
            )
        }

        Spacer(modifier = Modifier.height(128.dp))

        Text(
            text = "Don't have an account? Sign Up",
            style = TextStyle(
                color = Color.White,
                fontSize = 14.sp
            )
        )
    }
}
@Composable
fun Introduction() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = "Background"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Start streaming now with Black TV",
            modifier = Modifier.fillMaxWidth(0.7f),
            style = TextStyle(
                color = Color.White,
                fontSize = 22.sp,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
fun LoginMethods(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        val loginButtonModifier = Modifier
            .fillMaxWidth()

        LoginButton(methodName = "Log In", modifier = loginButtonModifier)

        Spacer(modifier = Modifier.height(8.dp))

        LoginButton(
            methodName = "Log In via Apple",
            icon = ImageVector.vectorResource(id = R.drawable.icons8_apple),
            modifier = loginButtonModifier
        )

        Spacer(modifier = Modifier.height(8.dp))

        LoginButton(
            methodName = "Log In via Google",
            icon = ImageVector.vectorResource(id = R.drawable.icons8_google),
            modifier = loginButtonModifier)
    }
}

@Composable
private fun LoginButton(
    methodName: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    onClick: () -> Unit = {}
) {
    val strokeBrush = Brush.linearGradient(
        colors = listOf(
            Cyan90,
            Blue90
        )
    )

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(10.dp),
        colors = buttonColors(
            containerColor = LightGrey30,
            contentColor = Color.White,
            disabledContainerColor = LightGrey30,
            disabledContentColor = Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            brush = strokeBrush
        ),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        if (icon != null) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(24.dp))
        }

        Text(
            text = methodName,
            style = TextStyle(
                fontSize = 16.sp
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoginButtonPreview() {
    LoginButton(
        methodName = "Login with Google",
        icon = Icons.Filled.Email, // Replace with your actual icon
        modifier = Modifier.width(200.dp) // Adjust width as needed
    )
}

@Preview
@Composable
fun LoginScreenPreview() {
    AuthenticationScreen()
}