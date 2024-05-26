package com.example.warehousemanagment.model.classes

import androidx.navigation.NavGraph

import androidx.navigation.NavAction

import androidx.navigation.NavDestination

import android.os.Bundle

import androidx.annotation.IdRes

import androidx.navigation.NavController

import androidx.navigation.NavDirections


object NavigationUtils
{
    /**
     * This function will check navigation safety before starting navigation using direction
     *
     * @param navController NavController instance
     * @param direction     navigation operation
     */
    fun navigateSafe(navController: NavController, direction: NavDirections) {
        val currentDestination = navController.currentDestination
        if (currentDestination != null) {
            val navAction = currentDestination.getAction(direction.actionId)
            if (navAction != null) {
                val destinationId = orEmpty(navAction.destinationId)
                val currentNode: NavGraph?
                currentNode =
                    if (currentDestination is NavGraph) currentDestination else currentDestination.parent
                if (destinationId != 0 && currentNode != null && currentNode.findNode(destinationId) != null) {
                    navController.navigate(direction)
                }
            }
        }
    }

    /**
     * This function will check navigation safety before starting navigation using resId and args bundle
     *
     * @param navController NavController instance
     * @param resId         destination resource id
     * @param args          bundle args
     */
    fun navigateSafe(navController: NavController, @IdRes resId: Int, args: Bundle?)
    {
        val currentDestination = navController.currentDestination
        if (currentDestination != null) {
            val navAction = currentDestination.getAction(resId)
            if (navAction != null) {
                val destinationId = orEmpty(navAction.destinationId)
                val currentNode: NavGraph?
                currentNode =
                    if (currentDestination is NavGraph) currentDestination else currentDestination.parent
                if (destinationId != 0 && currentNode != null && currentNode.findNode(destinationId) != null) {
                    navController.navigate(resId, args)
                }
            }
        }
    }

    private fun orEmpty(value: Int?): Int {
        return value ?: 0
    }
}