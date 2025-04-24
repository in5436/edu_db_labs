# Проєктування бази даних

## Модель бізнес-об'єктів

@startuml
' ==== Глобальне оформлення ====
skinparam backgroundColor #FAFAFA
skinparam shadowing false
skinparam class {
    BorderColor #455A64
    ArrowColor  #455A64
}

' ==== СУТНОСТІ ====
'— User —
entity User               #FFCC99
entity User.id            #FFE0B2
entity User.username      #FFE0B2
entity User.firstName     #FFE0B2
entity User.lastName      #FFE0B2
entity User.email         #FFE0B2
entity User.password      #FFE0B2

'— Role —
entity Role               #99CCFF
entity Role.name          #BBDEFB
entity Role.description   #BBDEFB
entity Role.id            #BBDEFB

'— Permission —
entity Permission         #CCFFCC
entity Permission.name    #E8F5E9
entity Permission.id      #E8F5E9

'— MediaRequest —
entity MediaRequest             #FFF59D
entity MediaRequest.id          #FFF9C4
entity MediaRequest.name        #FFF9C4
entity MediaRequest.type        #FFF9C4
entity MediaRequest.keywords    #FFF9C4
entity MediaRequest.description #FFF9C4
entity MediaRequest.updatedAt   #FFF9C4
entity MediaRequest.createdAt   #FFF9C4

'— Source —
entity Source             #FF99CC
entity Source.id          #F8BBD0
entity Source.name        #F8BBD0
entity Source.url         #F8BBD0

'— BasedOn | Label —
entity BasedOn            #CFD8DC
entity Label              #CFD8DC

'— Tag —
entity Tag                #D1C4E9
entity Tag.name           #EDE7F6
entity Tag.id             #EDE7F6

'— State —
entity State              #B0BEC5
entity State.id           #CFD8DC
entity State.displayName  #CFD8DC

'— Action —
entity Action             #B2EBF2
entity Action.createdAt   #E0F7FA

'— Feedback —
entity Feedback           #FFD180
entity Feedback.id        #FFE0B2
entity Feedback.body      #FFE0B2
entity Feedback.rating    #FFE0B2
entity Feedback.createdAt #FFE0B2
entity Feedback.updatedAt #FFE0B2

'— Role ↔ Permission зв’язка —
entity RoleHasPermission  #CFD8DC

' ==== ЗВ’ЯЗКИ ====
'--- User internal fields ---
User.username -r-* User
User.firstName -u-* User
User.lastName  -u-* User
User.email     -u-* User
User.password  -u-* User
User.id        -l-* User

'--- Role internal fields ---
Role.name        -u-* Role
Role.description -u-* Role
Role.id          -u-* Role

'--- User ↔ Role ---
User "0.*" -d- "1.1" Role

'--- Permission internal fields ---
Permission -d-* Permission.name
Permission -d-* Permission.id

'--- Role ↔ Permission (через RoleHasPermission) ---
Role "1.1"      -d- "0.*" RoleHasPermission
Permission "1.1" -u- "0.*" RoleHasPermission

'--- MediaRequest internal fields ---
MediaRequest.id          -r-* MediaRequest
MediaRequest.name        --* MediaRequest
MediaRequest.type        -u-* MediaRequest
MediaRequest.keywords    -u-* MediaRequest
MediaRequest.description -u-* MediaRequest
MediaRequest -u-* MediaRequest.updatedAt
MediaRequest -u-* MediaRequest.createdAt

'--- User ↔ MediaRequest ---
User "1.1" -u- "0.*" MediaRequest

'--- Source internal fields ---
Source.id   -u-* Source
Source.name -u-* Source
Source.url  -u-* Source

'--- BasedOn / Label / Tag ---
Source  "1.1" -u- "0.*" BasedOn
BasedOn "0.*" -u- "1.1" MediaRequest

Source "1.1" -r- "0.*" Label
Label  "0.*" -- "1.1" Tag

'--- State internal fields ---
State.id          -u-* State
State.displayName -u-* State

'--- Action & його зв’язки ---
Action.createdAt  -u-* Action
Action "0.*" -u- "1.1" User
Action "0.*" -u- "0.1" MediaRequest
Action "0.*" -- "0.1" Source
Action "0.*" -- "1.1" State

'--- Feedback & його зв’язки ---
User        "1.1" -u- "0.*" Feedback
MediaRequest "1.1" -r- "0.*" Feedback

Feedback -r-* Feedback.id
Feedback -d-* Feedback.body
Feedback -u-* Feedback.rating
Feedback -u-* Feedback.createdAt
Feedback -u-* Feedback.updatedAt
@enduml


## ER-модель

@startuml
' -------- глобальне оформлення --------
skinparam backgroundColor #FAFAFA
skinparam shadowing false
skinparam class {
    BorderColor #455A64
    ArrowColor  #455A64
}

' ======================================
'            NAMESPACE: AccountManagement
' ======================================
namespace AccountManagement {
    entity User <<ENTITY>> #FFCC99 {
        id: Int
        username: Text
        firstName: Text
        lastName: Text
        email: Text
        password: Text
    }
}

' ======================================
'            NAMESPACE: AccessPolicy
' ======================================
namespace AccessPolicy {
    entity Role <<ENTITY>> #FFF59D {
        id: Int
        name: Text
        description: Text
    }

    object UserRole               #FFFFFF
    object TechnicalExpertRole    #FFFFFF

    entity Permission <<ENTITY>> #CCFFCC {
        id: Int
        name: Text
    }

    entity RoleHasPermission <<ENTITY>> #CFD8DC {
        roleId: Int
        permissionId: Int
    }
}

' ======================================
'         NAMESPACE: MediaContentManagement
' ======================================
namespace MediaContentManagement {
    entity MediaRequest <<ENTITY>> #FFF9C4 {
        id: Int
        name: Text
        type: Text
        keywords: Text
        description: Text
        updatedAt: Datetime
        createdAt: Datetime
    }

    object Subscribe   #FFFFFF
    object Unsubscribe #FFFFFF
    object Quarantine  #FFFFFF

    entity Feedback <<ENTITY>> #FFD180 {
        id: Int
        body: Text
        rating: Float
        updatedAt: Datetime
        createdAt: Datetime
    }

    entity Source <<ENTITY>> #F8BBD0 {
        id: Int
        name: Text
        url: Int
    }

    entity BasedOn <<ENTITY>> #CFD8DC {
        mediaRequestId: Int
        sourceId: Int
    }

    entity Action <<ENTITY>> #B2EBF2 {
        createdAt: Datetime
        stateId: Int
        mediaRequestId: Int
        sourceId: Int
        userId: Int
    }

    entity State <<ENTITY>> #E1BEE7 {
        id: Int
        displayName: Text
    }

    entity Label <<ENTITY>> #CFD8DC {
        tagId: Int
        sourceId: Int
    }

    entity Tag <<ENTITY>> #D1C4E9 {
        id: Int
        name: Text
    }
}

' ======================================
'               ЗВ’ЯЗКИ
' ======================================
' User ↔ Role
AccountManagement.User "0.*" -d- "1.1" AccessPolicy.Role

' Role ↔ Permission
AccessPolicy.Role "1.1"      -d- "0.*" AccessPolicy.RoleHasPermission
AccessPolicy.Permission "1.1" -u- "0.*" AccessPolicy.RoleHasPermission

' User ↔ MediaRequest
AccountManagement.User "1.1" -u- "0.*" MediaContentManagement.MediaRequest

' Source ↔ MediaRequest (BasedOn)
MediaContentManagement.Source  "1.1" -u- "0.*" MediaContentManagement.BasedOn
MediaContentManagement.BasedOn "0.*" -u- "1.1" MediaContentManagement.MediaRequest

' Source ↔ Tag (Label)
MediaContentManagement.Source "1.1" -r- "0.*" MediaContentManagement.Label
MediaContentManagement.Label  "0.*" -- "1.1" MediaContentManagement.Tag

' Action ↔ усі решта
MediaContentManagement.Action "0.*" -u- "1.1" AccountManagement.User
MediaContentManagement.Action "0.*" -u- "0.1" MediaContentManagement.MediaRequest
MediaContentManagement.Action "0.*" -- "0.1" MediaContentManagement.Source
MediaContentManagement.Action "0.*" -- "1.1" MediaContentManagement.State

' Feedback ↔ User / MediaRequest
AccountManagement.User              "1.1" -u- "0.*" MediaContentManagement.Feedback
MediaContentManagement.MediaRequest "1.1" -r- "0.*" MediaContentManagement.Feedback

' Об’єктні (рольові/стани) залежності
AccessPolicy.UserRole            .u.> AccessPolicy.Role
AccessPolicy.TechnicalExpertRole .u.> AccessPolicy.Role
MediaContentManagement.Quarantine  .u.> MediaContentManagement.State
MediaContentManagement.Unsubscribe .u.> MediaContentManagement.State
MediaContentManagement.Subscribe   .u.> MediaContentManagement.State
@enduml


## Реляційна схема

<p align="center">
  <img src="./media/relationalSchema.png" width="600">
</p>