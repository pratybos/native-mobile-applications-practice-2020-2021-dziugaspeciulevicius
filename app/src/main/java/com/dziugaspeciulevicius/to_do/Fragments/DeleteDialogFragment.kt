import com.dziugaspeciulevicius.to_do.Models.Item
import com.dziugaspeciulevicius.to_do.R
import com.dziugaspeciulevicius.to_do.ViewModels.TodoViewModel

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import kotlinx.android.synthetic.main.fragment_delete_dialog.view.*

class DeleteDialogFragment(itemList: TodoViewModel, item: Item): DialogFragment() {

    val todoViewModel = itemList
    val item = item

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.fragment_delete_dialog, container, false)
        rootView.buttonCancel.setOnClickListener {
            dismiss()
        }

        rootView.buttonDelete.setOnClickListener {
            todoViewModel.removeItemFromTodoList(item)
            dismiss()
        }


        return rootView
    }
}