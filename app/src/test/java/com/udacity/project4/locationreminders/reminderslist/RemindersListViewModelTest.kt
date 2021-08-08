package com.udacity.project4.locationreminders.reminderslist

import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.MyApp
import com.udacity.project4.locationreminders.MainCoroutineRule
import com.udacity.project4.locationreminders.data.FakeReminderRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.MatcherAssert
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [Build.VERSION_CODES.P])
@ExperimentalCoroutinesApi
class RemindersListViewModelTest : KoinTest {

    // Use a fake repository to be injected into the viewmodel
    lateinit var reminderRepository: FakeReminderRepository

    // Subject under test
    private lateinit var remindersViewModel: RemindersListViewModel


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Before
    fun setupReminderRepository() {
        // Initialise the repository with some fake reminders.
        reminderRepository = FakeReminderRepository()
        reminderRepository.addSomeFakeData()

        remindersViewModel = RemindersListViewModel(MyApp(), reminderRepository)
    }

    @Test
    fun getReminderList() {

        remindersViewModel.loadReminders()

        MatcherAssert.assertThat(remindersViewModel.remindersList.value, IsEqual(convertRepositoryTest()))
    }

    fun convertRepositoryTest() : List<ReminderDataItem>{
        return listOf(
                ReminderDataItem.getFromReminderDTO(reminderRepository.reminderList[0]),
                ReminderDataItem.getFromReminderDTO(reminderRepository.reminderList[1]))
    }
}