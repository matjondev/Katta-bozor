package uz.usoft.merchapp.utils.extentions

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Size
import android.util.TypedValue
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.core.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import kotlin.math.roundToInt
import kotlin.math.roundToLong




val View.inflater get() = LayoutInflater.from(context)

operator fun <V : ViewBinding> V.invoke(init: V.() -> Unit) = init()


fun Fragment.finishFragment() {
    findNavController().popBackStack()
}

val Fragment.navController get() = findNavController()

context(Fragment)
fun NavDirections.navigate() {
    navController.navigate(this)
}





