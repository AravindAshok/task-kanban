//import "bootstrap/dist/css/bootstrap.min.css";
import "bootswatch/dist/darkly/bootstrap.min.css";

import { Navbar, Nav, Container, Row, Col, Card, Button } from "react-bootstrap";

const React = require('react');
const ReactDOM = require('react-dom');

const BASE_URL = "http://localhost:8081"

//these constants represent the enums for the statuses
const STATUS_CREATED = "CREATED";
const STATUS_TO_DO = "TO_DO";
const STATUS_IN_PROGRESS = "IN_PROGRESS";
const STATUS_DONE = "DONE";

const STATUSES_ENUM_TO_VALUE = {
  "CREATED": "CREATED",
  "TO_DO": "TO DO",
  "IN_PROGRESS": "IN PROGRESS",
  "DONE": "DONE"
}

const STATUSES_VALUE_TO_ENUM = {
  "CREATED": STATUS_CREATED,
  "TO DO": STATUS_TO_DO,
  "IN PROGRESS": STATUS_IN_PROGRESS,
  "DONE": STATUS_DONE
}

const NEXT_STATUS = {
  "CREATED": "TO_DO",
  "TO_DO": "IN_PROGRESS",
  "IN_PROGRESS": "DONE",
  "DONE": "TO_DO"
}

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {activeStatus: STATUS_TO_DO,
		              taskListKey: STATUS_TO_DO,
		              commonTaskListKey: STATUS_CREATED
		              };
		this.handleActiveStatusChange = this.handleActiveStatusChange.bind(this);
		this.getTasksByStatusEndpoint = this.getTasksByStatusEndpoint.bind(this);
		this.getCommonTasksEndpoint = this.getCommonTasksEndpoint.bind(this);
	  this.getCurrentTimestamp = this.getCurrentTimestamp.bind(this);
	}

	getCurrentTimestamp() {
	  return new Date().getTime();
	}

	handleActiveStatusChange(prevStatus, newStatus) {
	  var taskListKey = newStatus;
	  var commonTaskListKey = STATUS_CREATED;
	  if(prevStatus == STATUS_CREATED) {
      const currentTimestamp = this.getCurrentTimestamp();

	    commonTaskListKey = STATUS_CREATED + currentTimestamp;
      if(newStatus == this.state.activeStatus) {
        taskListKey = newStatus + currentTimestamp;
      }
	  }
	  this.setState({activeStatus: newStatus,
	                 taskListKey: taskListKey,
	                 commonTaskListKey: commonTaskListKey
	                });
	}

//---------------------------------------------
/*
  One of the following methods should be sent as the endpointFetcher prop while calling TaskList component.
  That would be the endpoint that TaskList component will call to fetch tasks that need to be shown.
*/
	getTasksByStatusEndpoint(status) {
	  return ("/tasks/tasksByStatus?status=" + status)
	}

  getCommonTasksEndpoint(status) {
    return "/tasks/common";
	}
//-----------------------------------------------

	render() {
	  const activeStatus = this.state.activeStatus;
	  const taskListKey = this.state.taskListKey;
	  const commonTaskListKey = this.state.commonTaskListKey;
    return (
      <div>
        <Navbar bg="primary">
          <Navbar.Brand><h1>Chore Kanban</h1></Navbar.Brand>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
        </Navbar>
        <br/>
        <Container>
          <Row>
            <Col md={2} sm={2}>
              <h3>Status</h3>
              <Nav
                variant="pills"
                className="flex-column"
                activeKey={activeStatus}
                onSelect = {
                  (status) => {
                    this.setState({activeStatus: status,
                                   taskListKey: status
                                  });
                  }
                }
               >
                <Nav.Item>
                  <Nav.Link eventKey={STATUS_TO_DO}>{STATUSES_ENUM_TO_VALUE[STATUS_TO_DO]}</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link eventKey={STATUS_IN_PROGRESS}>{STATUSES_ENUM_TO_VALUE[STATUS_IN_PROGRESS]}</Nav.Link>
                </Nav.Item>
                <Nav.Item>
                  <Nav.Link eventKey={STATUS_DONE}>{STATUSES_ENUM_TO_VALUE[STATUS_DONE]}</Nav.Link>
                </Nav.Item>
              </Nav>
            </Col>
            <Col md={4} sm={4}>
              <TaskList key={taskListKey} status={activeStatus} endpointFetcher={this.getTasksByStatusEndpoint} onActiveStatusChange={this.handleActiveStatusChange} />
            </Col>
            <Col md={4} sm={4}>
              <h2>Common Tasks</h2>
              <TaskList key={commonTaskListKey} status={STATUS_CREATED} endpointFetcher={this.getCommonTasksEndpoint} onActiveStatusChange={this.handleActiveStatusChange} />
            </Col>
          </Row>
        </Container>
      </div>
    );
	}
}

class TaskList extends React.Component {
	constructor(props) {
		super(props);
		this.state = {tasks: null};
		this.handleActiveStatusChange = this.handleActiveStatusChange.bind(this);
	}

	handleActiveStatusChange(prevStatus, newStatus) {
	  this.props.onActiveStatusChange(prevStatus, newStatus);
	}

	componentDidMount() {
	  const status = this.props.status;
	  const endpoint = this.props.endpointFetcher(status);
	  const URL = BASE_URL + endpoint;

	  fetch(URL)
    .then(response => response.json())
    .then(data => this.setState({tasks: data}));
	}

	render() {
    if (!this.state.tasks) {
      return <p>Loading</p>
    }

    const tasksFinal = this.state.tasks.map(task =>
      {
        const taskKey = task.title + task.status;
        return <p><Task key={taskKey} task={task} onActiveStatusChange={this.handleActiveStatusChange} /></p>
      }
    );
    return (
      tasksFinal
    )
	}

}

class Task extends React.Component{
  constructor() {
    super()
    this.state = {task: null}
    this.updateStatusHandler = this.updateStatusHandler.bind(this)
    this.handleActiveStatusChange = this.handleActiveStatusChange.bind(this);
    this.updateStatusAPICall = this.updateStatusAPICall.bind(this);
    this.updateStatusPromise = this.updateStatusPromise.bind(this);
  }

  componentDidMount() {
	  this.setState({task: this.props.task})
  }

  handleActiveStatusChange(prevStatus, newStatus) {
    this.props.onActiveStatusChange(prevStatus, newStatus);
  }

  updateStatusHandler() {
    var task = this.state.task;
    const prevStatus = task.status;
    const newStatus = NEXT_STATUS[this.state.task.status];
    this.updateStatusPromise(task.id, newStatus)
      .then(() => this.handleActiveStatusChange(prevStatus, newStatus));
  }

  updateStatusPromise(taskId, newStatus) {
    return Promise.all([this.updateStatusAPICall(taskId, newStatus)])
  }

  updateStatusAPICall(taskId, newStatus) {
    const updateStatusEndpoint = BASE_URL + "/tasks/" + taskId + "/updateStatus?status=" + newStatus;
    return fetch(updateStatusEndpoint,
      {
        method: 'post'
      })
      .then(response => response.json);
  }

	render() {
	  if(!this.state.task) return <p>Loading...</p>
	  const nextStatus = NEXT_STATUS[this.state.task.status];
		return (
		    <Card className="text-center" style={{ width: '18rem' }}>
        <Card.Body>
          <Card.Title>{this.state.task.title}</Card.Title>
          <Card.Text>{this.state.task.description}</Card.Text>
          <Button onClick={this.updateStatusHandler}>{STATUSES_ENUM_TO_VALUE[nextStatus]}</Button>
        </Card.Body>
        <Card.Footer className="text-muted">{this.state.task.points} Points</Card.Footer>
      </Card>
		)
	}
}


ReactDOM.render(
	<App />,
	document.getElementById('react')
)