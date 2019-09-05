## User Requirement
1. User could belong to hasMany Access Group [Administration, Faculty]
2. User could have multiple adhock access, such as An user belongs to Faculty Group but we want to give some specific 
administrative access, that's User could have hasMany Access List.


## User Access Group
1. User Access Group basically represent Unit of Access list, such as **Administrator**
can belong to all of access, But the **Faculty** not belong to whole access.
2. We can create Access Group with Access List
3. If anyone specify the group is belong to ALL_ACCESS then not need to specify any other access specifically
4. if Any Access group deleted or inActive then that group user's not able to access anything
5. There will be some pre defined Access Group [Administrator, Manager, Staff]
6. Assign as Add-hock Access Mean, The user all permission will assign specifically and we can the revoke
some access manually.
7. Revoke Add-hock Access: Revoke all Add-hock access from that user Automatically
8. User Access Group will have hasMany or hasOne User


## User Access List
1. Hold the all end point name, suppose to [User, Access Group]
2. We can assign access directly from user and using Access Group
3. If we set ALL_ACCESS into a end point then all action will allowed for them
4. Access List HasMany Access Action which specify individual action [create, edit, list]



