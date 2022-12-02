package com.example.spinningwheel
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.rememberNavController

@Composable

fun Navigation(){
    val navController = rememberNavController()

    Card(elevation = 2.dp){
        Image(contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.wheelstartscreen),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

        NavHost(navController = navController, startDestination = Screen.MainScreen.route) {

            composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
            }

            composable(route = Screen.DetailScreen.route){
            DetailScreen(navController = navController)
            }

            composable(route = Screen.WinScreen.route){
            WinScreen(navController = navController)
            }

            composable(route = Screen.LoseScreen.route){
                LoseScreen(navController = navController)
            }

        }
    }
}


@Composable
fun MainScreen(navController: NavController){

    var text by remember {
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {

        Text(text = "WHEEL OF FORTUNE",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 50.dp).align(CenterHorizontally)
        )

        Image(painter = painterResource(id = R.drawable.screenshot_2022_11_29_at_15_42_37_removebg_preview),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 10.dp)
                .align(CenterHorizontally)
        )


        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
               navController.navigate(Screen.DetailScreen.route)
        },
        modifier = Modifier.padding(vertical = 30.dp).align(Alignment.CenterHorizontally)) {
            Text(text = "Start Game")
        }


        Text(text = "Game Rules: "  )
        Text(text = "\n1) You have to spin before you guess, otherwise the game will crash, this will be patched in the next update ")
        Text(text = "\n2) You must guess after your spin, and after your guess you must spin again! (Dont cheat)")
        Text(text = "\n3) You can guess the whole word when you are ready!")
        Text(text = "\n4) I understand that this versions architecture is very bad, due to problems with my computer and Andriod Studio, Model - View - Controller has not been followed. (Will be patched next update) ")
    }
}


@Composable
fun DetailScreen(navController: NavController){

    Card(elevation = 2.dp){
        Image(contentScale = ContentScale.FillBounds,
            painter = painterResource(id = R.drawable.wheelbackground),
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )

    var viewModel = ViewModel()
    var category by remember {
        mutableStateOf("")
    }
    var word by remember {
        mutableStateOf(" ")
    }
    var cur by remember {
        mutableStateOf("")
    }

    var text by remember {
        mutableStateOf("")
    }

    var possiblePoint by remember {
        mutableStateOf("")
    }

    var balance by remember {
        mutableStateOf(0)
    }

    var startLives by remember {
        mutableStateOf(5)
    }



    Column(
        modifier = Modifier
            .fillMaxSize()

    ){
        Text(text = "Balance: " + balance)
        Text(text = "Lives Remaining: " + startLives)

        Image(painter = painterResource(id = R.drawable.screenshot_2022_11_29_at_15_42_37_removebg_preview),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 50.dp)
                .align(CenterHorizontally)
                )

        //Spacer(modifier = Modifier.height(1.dp))
        if (possiblePoint > 0.toString()){
            Text(text = "You will get " +  possiblePoint + " Points if you guess correctly", modifier = Modifier.align(CenterHorizontally))
        }
        if (possiblePoint == 0.toString()){
            Text(text = "You are Bankrupt! Your Balance is now : " +  possiblePoint, modifier = Modifier.align(CenterHorizontally))
        }
        Button(onClick = {
                if (category == ""){
                    category = viewModel.randomCategory()
                    word = viewModel.randomWord(category)
                    cur = viewModel.setupWord(word)

                }

            possiblePoint = viewModel.pointSystem().toString()

            if (possiblePoint == 0.toString()){
                balance = 0
            }

        }, modifier = Modifier.align(CenterHorizontally)) {
            Text(text = "Spin")
        }

        Text(text = "Category: " +category, modifier = Modifier.align(CenterHorizontally))
        //Text(text = word, modifier = Modifier.align(CenterHorizontally))
        Text(text = cur, modifier = Modifier.align(CenterHorizontally))


        Spacer(modifier = Modifier.height(25.dp))

        TextField(value = text,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .align(CenterHorizontally)
                .width(100.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {


           if(!cur.contains(text)){
               balance += viewModel.updatePoint(word,text.toCharArray()[0], points = possiblePoint.toInt())
           }

           if(cur.contains(text)){
               startLives--
           }

          if(!word.contains(text)){
              startLives--
          }
            cur = viewModel.checkWord(word,text.toCharArray()[0],cur)

            if (text == word){
                navController.navigate(Screen.WinScreen.route)
            }

        },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Guess")
        }

        if (cur == word){
            navController.navigate(Screen.WinScreen.route)
        }

        if (startLives == 0){
            navController.navigate(Screen.LoseScreen.route)
        }

    }


}
}

@Composable
fun WinScreen(navController: NavController) {

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.my_project),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 50.dp)
                .align(CenterHorizontally)
                .height(350.dp)
                .width(350.dp)
        )

        Button(onClick = {
            navController.navigate(Screen.DetailScreen.route)
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Play Again")
        }

        Button(onClick = {
            navController.navigate(Screen.MainScreen.route)
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Home")
        }


    }


}



@Composable
fun LoseScreen(navController: NavController){

    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Image(
            painter = painterResource(id = R.drawable.my_project_2),
            contentDescription = null,
            modifier = Modifier
                .padding(vertical = 50.dp)
                .align(CenterHorizontally)
                .height(350.dp)
                .width(350.dp)
        )

        Button(onClick = {
            navController.navigate(Screen.DetailScreen.route)
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Play Again")
        }

        Button(onClick = {
            navController.navigate(Screen.MainScreen.route)
        },
            modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(text = "Home")
        }


    }

}